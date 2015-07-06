package res.truecaller.sample.com.truecallersample.view;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.R;
import res.truecaller.sample.com.truecallersample.model.Operation;
import res.truecaller.sample.com.truecallersample.control.OperationCallback;
import res.truecaller.sample.com.truecallersample.control.TruecallerTestTask;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Button request_button;

    TruecallerTestTask firstTenthElement;
    TruecallerTestTask allTenthElements;
    TruecallerTestTask wordCountResult;

    TextView request_1_result;
    ProgressBar request_progressBar1;
    TextView request_1_result_details;

    TextView request_2_result;
    ProgressBar request_progressBar2;
    TextView request_2_result_details;

    TextView request_3_result;
    ProgressBar request_progressBar3;
    TextView request_3_result_details;

    ScrollView content_scrollview;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FragmentDrawer mFragmentDrawer;
    private List<TruecallerTestTask> runningTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout  =  (DrawerLayout) findViewById(R.id.drawer_layout);

        content_scrollview = (ScrollView) findViewById(R.id.content_scrollview);

        mFragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, mToolbar);
        mFragmentDrawer.setDrawerListener(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        runningTasks = new ArrayList<TruecallerTestTask>();

        request_button = (Button) findViewById(R.id.request_button);

        request_1_result = (TextView) findViewById(R.id.request_1_result);
        request_progressBar1 = (ProgressBar) findViewById(R.id.request_progressBar1);
        request_1_result_details = (TextView) findViewById(R.id.request_1_result_details);

        request_2_result = (TextView) findViewById(R.id.request_2_result);
        request_progressBar2 = (ProgressBar) findViewById(R.id.request_progressBar2);
        request_2_result_details = (TextView) findViewById(R.id.request_2_result_details);

        request_3_result = (TextView) findViewById(R.id.request_3_result);
        request_progressBar3 = (ProgressBar) findViewById(R.id.request_progressBar3);
        request_3_result_details = (TextView) findViewById(R.id.request_3_result_details);

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_button.setEnabled(false);

                resetProgressBars();
                initAllTasks();
                resetResultFields();

                firstTenthElement.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                allTenthElements.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                wordCountResult.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });


    }

    public void initAllTasks(){
        firstTenthElement = new TruecallerTestTask(this, Operation.FIRST_TENTH_ELEMENT, new OperationCallback() {
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
                if(runningTasks.isEmpty())
                    request_button.setEnabled(true);
            }

            @Override
            public void onOperationCancelled() {
                request_1_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_progressBar1.setProgress(progressPercent);
                request_progressBar1.setSecondaryProgress(progressPercent + 5);
            }

            @Override
            public void processFinalResult(Object object) {
                request_1_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {

            }

            @Override
            public void onTenthChar(char ch) {
                super.onTenthChar(ch);
                request_1_result_details.setText("onTenthChar: \'" + ch + "\'");
            }
        });


        allTenthElements = new TruecallerTestTask(this, Operation.ALL_TENTH_ELEMENT, new OperationCallback() {
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
                if(runningTasks.isEmpty())
                    request_button.setEnabled(true);
            }

            @Override
            public void onOperationCancelled() {
                request_2_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_progressBar2.setProgress(progressPercent);
                request_progressBar2.setSecondaryProgress(progressPercent + 5);

            }

            @Override
            public void processFinalResult(Object object) {
                request_2_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {

            }

            @Override
            public void onAllTenthCharsList(List<Character> ch) {
                super.onAllTenthCharsList(ch);
                request_2_result_details.setText("onAllTenthCharsList: \'" + ch + "\'");
            }

        });


        wordCountResult = new TruecallerTestTask(this, Operation.WORD_GET, new OperationCallback() {
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
                if(runningTasks.isEmpty())
                    request_button.setEnabled(true);
            }

            @Override
            public void onOperationCancelled() {
                request_3_result_details.setText("onOperationCancelled");
            }

            @Override
            public void onProgressUpdated(int progressPercent) {
                request_progressBar3.setProgress(progressPercent);
                request_progressBar3.setSecondaryProgress(progressPercent + 5);
            }

            @Override
            public void processFinalResult(Object object) {
                request_3_result_details.setText("processFinalResult");
            }

            @Override
            public void useStringResult(String result) {

            }

            @Override
            public void onAllRepeatedWordWithCount(HashMap<String, Integer> wordCount) {
                super.onAllRepeatedWordWithCount(wordCount);
                request_3_result_details.setText("onAllRepeatedWordWithCount: \'" + wordCount+"\'");
            }
        });
    }

    public void resetProgressBars(){
        request_progressBar1.setProgress(0);
        request_progressBar1.setSecondaryProgress(0 + 5);
        request_progressBar2.setProgress(0);
        request_progressBar2.setSecondaryProgress(0 + 5);
        request_progressBar3.setProgress(0);
        request_progressBar3.setSecondaryProgress(0 + 5);
    }

    public void resetResultFields(){
        request_1_result_details.setText(getResources().getString(R.string.request_1_text));
        request_2_result_details.setText(getResources().getString(R.string.request_2_text));
        request_3_result_details.setText(getResources().getString(R.string.request_3_text));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                if(mDrawerLayout!=null) {
                    if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayView(int position){
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position){
            case 0:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_1_result.getTop());
                    }
                });
                break;
            case 1:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_2_result.getTop());
                    }
                });
                break;
            case 2:
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        content_scrollview.scrollTo(0, request_3_result.getTop());
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
}
