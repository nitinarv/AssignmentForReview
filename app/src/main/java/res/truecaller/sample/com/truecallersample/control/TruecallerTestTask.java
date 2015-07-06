package res.truecaller.sample.com.truecallersample.control;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.model.Operation;
import res.truecaller.sample.com.truecallersample.model.ResponseDetails;
import res.truecaller.sample.com.truecallersample.model.TaskResult;
import res.truecaller.sample.com.truecallersample.model.Types;


/**
 * Created by nitinraj.arvind on 7/6/2015.
 */
public class TruecallerTestTask extends AsyncTask<Void,Object,TaskResult> {


    Operation operation = Operation.FIRST_TENTH_ELEMENT;
    OperationCallback operationCallback = null;
    RestRelatedWork jobs = null;
    Context context = null;

    private static final String STAGEUPDATE = "stage_update:";

    private int indexOfInterest = 10;

    TaskResult taskResult = null;
    private int progressCount = 0;

    public TruecallerTestTask(Context mContext, Operation mOperation, OperationCallback mOperationCallback) {
        this.operation = mOperation;
        this.context = mContext;
        this.operationCallback = mOperationCallback;
        this.taskResult = new TaskResult();
        this.jobs = RestRelatedWork.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        operationCallback.onProgressStarted();
        taskResult.setStartTime(new Date());
    }

    @Override
    protected TaskResult doInBackground(Void... params) {

        try {
            publishProgress(new Integer(progressCount+=5));
            ResponseDetails responseDetails = jobs.getWebPage(context);
            String responseString = responseDetails.getReponseString();
            switch (operation) {
                case FIRST_TENTH_ELEMENT:
                    if(responseString!=null) {
                        publishProgress(new Integer(progressCount+=50));
                        Character nthItem = responseString.charAt(indexOfInterest-1);
                        taskResult.setFirstTenthElement(nthItem);
                        publishProgress(new Integer(progressCount+=45));
                        return taskResult;
                    }
                    break;
                case ALL_TENTH_ELEMENT:
                    if(responseString!=null) {
                        List<Character> listOfCharacters = null;
                        publishProgress(new Integer(progressCount+=25));
                        for(int i=(indexOfInterest-1); i < responseString.length(); i = i + indexOfInterest){
                            if(listOfCharacters==null)
                                listOfCharacters = new ArrayList<Character>();
                            Character charAtIndex = responseString.charAt(i);
                            listOfCharacters.add(charAtIndex);
                        }
                        publishProgress(new Integer(progressCount += 70));
                        taskResult.setAllTenthElement(listOfCharacters);

                        return taskResult;
                    }
                    break;
                case WORD_GET:
                    if(responseString!=null){
                        publishProgress(new Integer(progressCount += 5));
                        String[] splited = responseString.split("\\s");
                        publishProgress(new Integer(progressCount += 5));
                        HashMap<String, Integer> wordCountMap = null;
                        publishProgress(new Integer(progressCount += 5));
                        for(String wordItem: splited){
                            if(wordCountMap==null)
                                wordCountMap = new HashMap<String, Integer>();

                            if(!wordCountMap.containsKey(wordItem.toLowerCase())){
                                wordCountMap.put(wordItem.toLowerCase(), 1);
                            }else{
                                wordCountMap.put(wordItem, (wordCountMap.get(wordItem.toLowerCase())+1));
                            }
                        }
                        publishProgress(new Integer(progressCount += 80));
                        taskResult.setUniqueWordCount(wordCountMap);
                        return taskResult;
                    }
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            publishProgress(e);
        }
        return null;
    }


    @Override
    protected void onPostExecute(TaskResult result) {
        super.onPostExecute(result);
        operationCallback.onProgressEnded();
        if(result!=null) {
            result.setEndTime(new Date());
            switch (operation) {
                case FIRST_TENTH_ELEMENT: {
                    if (result.getFirstTenthElement() != null) {
                        operationCallback.onTenthChar(result.getFirstTenthElement());
                    }
                }
                break;
                case ALL_TENTH_ELEMENT:
                    if (result.getAllTenthElement() != null) {
                        operationCallback.onAllTenthCharsList(result.getAllTenthElement());
                    }
                    break;
                case WORD_GET:
                    if (result.getUniqueWordCount() != null) {
                        operationCallback.onAllRepeatedWordWithCount(result.getUniqueWordCount());
                    }
                    break;
            }
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if(operationCallback!=null){
            switch (progressUpdateObject(values[0])){
                case EXCEPTION:
                    Exception e = (Exception)values[0];
                    operationCallback.processException(e);
                    break;
                case PROGRESSUPDATE:
                    Integer progressCount = (Integer) values[0];
                    operationCallback.onProgressUpdated(progressCount);
                    break;
                case STAGEUPDATE:
                    String data = (String) values[0];
                    data = data.replaceAll(STAGEUPDATE,"");
                    break;
                case UNDEFINED:
                    break;
            }

        }

    }

    /**
     *
     * */
    private Types progressUpdateObject(Object value){
        if(value instanceof Exception){
            return Types.EXCEPTION;
        }else if(value instanceof  Integer){
            return Types.PROGRESSUPDATE;
        }else if(value instanceof String){
            String data = (String) value;
            if(data.contains(STAGEUPDATE)){
                return Types.STAGEUPDATE;
            }else{
                return Types.UNDEFINED;
            }
        }else{
            return Types.UNDEFINED;
        }
    }

}
