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
            ResponseDetails responseDetails = jobs.getWebPage(context);
            String responseString = responseDetails.getReponseString();

            if(responseString!=null) {
                if (operationCallback instanceof First10thResultCallback) {

                    Character nthItem = responseString.charAt(indexOfInterest - 1);

                    taskResult.setFirstTenthElement(nthItem);
                    taskResult.setFirstTenthElementString(nthItem.toString());
                    return taskResult;

                } else if (operationCallback instanceof All10thResultCallback) {

                    List<Character> listOfCharacters = new ArrayList<Character>();

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = (indexOfInterest - 1); i < responseString.length(); i = i + indexOfInterest) {
                        Character charAtIndex = responseString.charAt(i);
                        listOfCharacters.add(charAtIndex);
                        stringBuilder.append(charAtIndex + ",\n");
                    }

                    taskResult.setAllTenthElementString(stringBuilder.toString());
                    taskResult.setAllTenthElement(listOfCharacters);

                    return taskResult;

                } else if (operationCallback instanceof RepeatedWordCountCallback) {

                    String[] splited = responseString.split("\\s+");
                    HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
                    ;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String wordItem : splited) {
                        if (!wordCountMap.containsKey(wordItem.toLowerCase())) {
                            wordCountMap.put(wordItem.toLowerCase(), 1);
                        } else {
                            wordCountMap.put(wordItem, (wordCountMap.get(wordItem.toLowerCase()) + 1));
                        }
                    }
                    //convert to string
                    Set<String> mapkeys = wordCountMap.keySet();
                    for (String entry : mapkeys) {
                        stringBuilder.append("key: " + entry + ", value(count): " + wordCountMap.get(entry) + "\n");
                    }

                    taskResult.setUniqueWordCountString(stringBuilder.toString());
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
