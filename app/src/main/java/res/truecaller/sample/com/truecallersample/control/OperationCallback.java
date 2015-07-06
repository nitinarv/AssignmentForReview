package res.truecaller.sample.com.truecallersample.control;

import java.util.HashMap;
import java.util.List;

/**
 * Created by  nitinraj.arvind on 06/07/15.
 */
public abstract class OperationCallback {


    public abstract void processException(Exception e);
    public abstract void onProgressStarted();
    public abstract void onProgressEnded();
    public abstract void onOperationCancelled();
    public abstract void onProgressUpdated(int progressPercent);
    public abstract void processFinalResult(Object object);
    public abstract void useStringResult(String result);

    /**
     * for operation FIRST_TENTH_ELEMENT
     * */
    public void onTenthChar(char ch){

    }

    /**
     * for operation ALL_TENTH_ELEMENT
     * */
    public void onAllTenthCharsList(List<Character> ch){

    }

    /**
     * for operation WORD_GET
     * */
    public void onAllRepeatedWordWithCount(HashMap<String, Integer> wordCount){

    }



}
