package res.truecaller.sample.com.truecallersample.control;

import java.util.HashMap;

/**
 * Created by nitinraj.arvind on 7/9/2015.
 */
public interface RepeatedWordCountCallback extends OperationCallback{
    public void onAllRepeatedWordWithCount(HashMap<String, Integer> wordCount);
}
