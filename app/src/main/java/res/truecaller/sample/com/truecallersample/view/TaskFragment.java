package res.truecaller.sample.com.truecallersample.view;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.R;
import res.truecaller.sample.com.truecallersample.control.All10thResultCallback;
import res.truecaller.sample.com.truecallersample.control.First10thResultCallback;
import res.truecaller.sample.com.truecallersample.control.OperationCallback;
import res.truecaller.sample.com.truecallersample.control.RepeatedWordCountCallback;
import res.truecaller.sample.com.truecallersample.control.TruecallerTestTask;

public class TaskFragment extends Fragment {

    TruecallerTestTask firstTenthElement;
    TruecallerTestTask allTenthElements;
    TruecallerTestTask wordCountResult;

    TextView request_1_result_details;
    TextView request_2_result_details;
    TextView request_3_result_details;
    ScrollView content_scrollview;

    MainActivity activity;

    private List<TruecallerTestTask> runningTasks;

    public static final String TAG_TASK_FRAGMENT = "res.truecaller.sample.com.truecallersample.view.TaskFragment";

    TaskCallbacks taskCallbacks;

    interface TaskCallbacks{
        public void enableButton();
        public void setWordData(HashMap<String, Integer> mWordMap);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
        activity = getActivity();
        taskCallbacks = (MainActivity) activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
//        if (container != null) {
            view = inflater.inflate(R.layout.fragment_task, container, false);
            content_scrollview = (ScrollView) view.findViewById(R.id.content_scrollview);
            request_1_result_details = (TextView) view.findViewById(R.id.request_1_result_details);
            request_2_result_details = (TextView) view.findViewById(R.id.request_2_result_details);
            request_3_result_details = (TextView) view.findViewById(R.id.request_3_result_details);

            runningTasks = new ArrayList<TruecallerTestTask>();

//        }else{
//            view = super.onCreateView(inflater, container, savedInstanceState);
//        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        if(runningTasks.isEmpty() && taskCallbacks!=null)
            taskCallbacks.enableButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
        taskCallbacks = null;
    }

    public void runAllTasks(){
        resetProgressBars();
        initAllTasks();
        resetResultFields();
        executeAllTasks();
    }


    /**
     * initing all the task objects for all the tasks.
     * */
    private void initAllTasks(){
        firstTenthElement = new TruecallerTestTask(getActivity(), new First10thResultCallback() {
            @Override
            public void processException(Exception e) {
                request_1_result_details.setText("processException: " + e.getMessage());
            }

            @Override
            public void onProgressStarted() {
                request_1_result_details.setText("onProgressStarted");
                runningTasks.add(firstTenthElement);

            }

            @Override
            public void onProgressEnded() {
                request_1_result_details.setText("onProgressEnded");
                runningTasks.remove(firstTenthElement);
                if(runningTasks.isEmpty() && taskCallbacks!=null)
                    taskCallbacks.enableButton();
            }

            @Override
            public void onOperationCancelled() {
                request_1_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_1_result_details.setText("" + progressPercent+"/100");
            }

            @Override
            public void processFinalResult(Object object) {
                request_1_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {
                request_1_result_details.setText("onTenthChar: \'" + result + "\'");
            }

            @Override
            public void onTenthChar(char ch) {

            }
        });


        allTenthElements = new TruecallerTestTask(getActivity(), new All10thResultCallback() {
            @Override
            public void processException(Exception e) {
                request_2_result_details.setText("processException: " + e.getMessage());
            }

            @Override
            public void onProgressStarted() {
                request_2_result_details.setText("onProgressStarted");
                runningTasks.add(allTenthElements);
            }

            @Override
            public void onProgressEnded() {
                request_2_result_details.setText("onProgressEnded");
                runningTasks.remove(allTenthElements);
                if(runningTasks.isEmpty() && taskCallbacks!=null)
                    taskCallbacks.enableButton();
            }

            @Override
            public void onOperationCancelled() {
                request_2_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_2_result_details.setText("" + progressPercent+"/100");
            }

            @Override
            public void processFinalResult(Object object) {
                request_2_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {
                request_2_result_details.setText("onAllTenthCharsList: \'" + result + "\'");
            }

            @Override
            public void onAllTenthCharsList(List<Character> ch) {

            }

        });


        wordCountResult = new TruecallerTestTask(getActivity(), new RepeatedWordCountCallback() {
            @Override
            public void processException(Exception e) {
                request_3_result_details.setText("processException: " + e.getMessage());
            }

            @Override
            public void onProgressStarted() {
                request_3_result_details.setText("onProgressStarted");
                runningTasks.add(wordCountResult);
            }

            @Override
            public void onProgressEnded() {
                request_3_result_details.setText("onProgressEnded");
                runningTasks.remove(wordCountResult);
                if(runningTasks.isEmpty() && taskCallbacks!=null)
                    taskCallbacks.enableButton();
            }

            @Override
            public void onOperationCancelled() {
                request_3_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_3_result_details.setText("" + progressPercent+"/100");
            }

            @Override
            public void processFinalResult(Object object) {
                request_3_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {
                request_3_result_details.setText("onAllRepeatedWordWithCount: \'" + result+"\'");
            }

            @Override
            public void onAllRepeatedWordWithCount(HashMap<String, Integer> wordCount) {
                if(taskCallbacks!=null)
                    taskCallbacks.setWordData(wordCount);
            }
        });
    }

    private void executeAllTasks(){
        firstTenthElement.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        allTenthElements.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        wordCountResult.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    /**
     * Visual reset of the view progress bars
     * */
    private void resetProgressBars(){

    }

    /**
     * Visual reset of the fields
     * */
    private void resetResultFields(){
        request_1_result_details.setText(getResources().getString(R.string.request_1_text));
        request_2_result_details.setText(getResources().getString(R.string.request_2_text));
        request_3_result_details.setText(getResources().getString(R.string.request_3_text));
    }

    public void displayView(int position){
        android.support.v4.app.Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position){
            case 0:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_1_result_details.getTop());
                    }
                });
                break;
            case 1:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_2_result_details.getTop());
                    }
                });
                break;
            case 2:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_3_result_details.getTop());
                    }
                });
                break;
            default:
                break;
        }
    }

}
