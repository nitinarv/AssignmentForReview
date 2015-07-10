package res.truecaller.sample.com.truecallersample.control;

import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.model.TaskResult;

/**
 * Created by  nitinraj.arvind on 06/07/15.
 */
public interface OperationCallback {


    public abstract void processException(Exception e);
    public abstract void onProgressStarted();
    public abstract void onProgressEnded();
    public abstract void onOperationCancelled();
    public abstract void onProgressUpdated(int progressPercent);
    public abstract void processFinalResult(Object object);
    public abstract void useStringResult(String result);
    public abstract void storeTaskResult(TaskResult taskResult);

}
