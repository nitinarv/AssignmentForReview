package res.truecaller.sample.com.truecallersample.control;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import res.truecaller.sample.com.truecallersample.model.ResponseDetails;
import res.truecaller.sample.com.truecallersample.model.TaskResult;


/**
 * Created by nitinraj.arvind on 7/6/2015.
 */
public class TruecallerTestTask extends AsyncTask<Void,Object,TaskResult> {


    OperationCallback operationCallback = null;
    RestRelatedWork jobs = null;
    Context context = null;

    private static final String STAGEUPDATE = "stage_update:";

    private int indexOfInterest = 10;

    TaskResult taskResult = null;
    private int progressCount = 0;

    public TruecallerTestTask(Context mContext, OperationCallback mOperationCallback) {
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

            if(operationCallback instanceof First10thResultCallback){
                if(responseString!=null) {
                    publishProgress(new Integer(progressCount+=50));
                    Character nthItem = responseString.charAt(indexOfInterest-1);
                    taskResult.setFirstTenthElement(nthItem);
                    publishProgress(new Integer(progressCount+=40));
                    taskResult.setFirstTenthElementString(nthItem.toString());
                    publishProgress(new Integer(progressCount+=45));
                    return taskResult;
                }
            }else if(operationCallback instanceof All10thResultCallback){
                if(responseString!=null) {
                    List<Character> listOfCharacters = null;
                    publishProgress(new Integer(progressCount+=25));
                    for(int i=(indexOfInterest-1); i < responseString.length(); i = i + indexOfInterest){
                        if(listOfCharacters==null)
                            listOfCharacters = new ArrayList<Character>();
                        Character charAtIndex = responseString.charAt(i);
                        listOfCharacters.add(charAtIndex);
                    }

                    publishProgress(new Integer(progressCount += 35));
                    StringBuilder stringBuilder = new StringBuilder();
                    //convert to string
                    for(Character entry: listOfCharacters){
                        stringBuilder.append(entry);
                        stringBuilder.append(",\n");
                    }

                    taskResult.setAllTenthElementString(stringBuilder.toString());

                    publishProgress(new Integer(progressCount += 35));
                    taskResult.setAllTenthElement(listOfCharacters);

                    return taskResult;
                }
            }else if(operationCallback instanceof RepeatedWordCountCallback){
                if(responseString!=null){
                    publishProgress(new Integer(progressCount += 5));
                    String[] splited = responseString.split("\\s+");
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
                    publishProgress(new Integer(progressCount += 40));
                    //convert to string
                    Set<String> mapkeys = wordCountMap.keySet();
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String entry: mapkeys){
                        stringBuilder.append("key: "+entry);
                        stringBuilder.append(", value(count): "+wordCountMap.get(entry));
                        stringBuilder.append("\n");
                    }

                    taskResult.setUniqueWordCountString(stringBuilder.toString());
                    publishProgress(new Integer(progressCount += 40));
                    taskResult.setUniqueWordCount(wordCountMap);
                    return taskResult;
                }
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
            if(operationCallback instanceof First10thResultCallback){
                if (result.getFirstTenthElement() != null) {
                    ((First10thResultCallback)operationCallback).onTenthChar(result.getFirstTenthElement());
                    operationCallback.useStringResult(result.getFirstTenthElementString());
                }
            }else if(operationCallback instanceof All10thResultCallback){
                if (result.getAllTenthElement() != null) {
                    ((All10thResultCallback)operationCallback).onAllTenthCharsList(result.getAllTenthElement());
                    operationCallback.useStringResult(result.getAllTenthElementString());
                }
            }else if(operationCallback instanceof RepeatedWordCountCallback){
                if (result.getUniqueWordCount() != null) {
                    ((RepeatedWordCountCallback)operationCallback).onAllRepeatedWordWithCount(result.getUniqueWordCount());
                    operationCallback.useStringResult(result.getUniqueWordCountString());
                }
            }
        }
    }
//
    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if(operationCallback!=null){
            if(values[0] instanceof Exception) {
                Exception e = (Exception) values[0];
                operationCallback.processException(e);
            }else if(values[0] instanceof Integer) {
                Integer progressCount = (Integer) values[0];
                operationCallback.onProgressUpdated(progressCount);
            }else if(values[0] instanceof String) {
                String data = (String) values[0];
            }
       }

    }

}
