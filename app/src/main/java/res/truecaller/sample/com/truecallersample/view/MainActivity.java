package res.truecaller.sample.com.truecallersample.view;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.R;
import res.truecaller.sample.com.truecallersample.control.All10thResultCallback;
import res.truecaller.sample.com.truecallersample.control.First10thResultCallback;
import res.truecaller.sample.com.truecallersample.control.RepeatedWordCountCallback;
import res.truecaller.sample.com.truecallersample.control.TruecallerTestTask;


public class MainActivity extends AppCompatActivity {

    Button request_button;

    TruecallerTestTask firstTenthElement;
    TruecallerTestTask allTenthElements;
    TruecallerTestTask wordCountResult;

    TextView request_1_result_details;

    TextView request_2_result_details;

    TextView request_3_result_details;

    ScrollView content_scrollview;

    private Toolbar mToolbar;
    private List<TruecallerTestTask> runningTasks;


    private List<String> wordList;
    private List<String> resultList;
    private HashMap<String, Integer> wordMap;
    private MenuItem mSearchAction;
    private boolean mSearchOpened;
    private SearchManager searchManager;
    private SearchView searchView;
    private SearchableInfo searchableInfo;
    private ExampleAdapter searchableAdapter;
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        content_scrollview = (ScrollView) findViewById(R.id.content_scrollview);

        setSupportActionBar(mToolbar);


        runningTasks = new ArrayList<TruecallerTestTask>();

        request_button = (Button) findViewById(R.id.request_button);

        request_1_result_details = (TextView) findViewById(R.id.request_1_result_details);

        request_2_result_details = (TextView) findViewById(R.id.request_2_result_details);

        request_3_result_details = (TextView) findViewById(R.id.request_3_result_details);

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_button.setEnabled(false);

                resetProgressBars();
                initAllTasks();
                resetResultFields();
                wordList = null;

                firstTenthElement.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                allTenthElements.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                wordCountResult.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        mSearchOpened = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final Menu fMenu = menu;
        searchItem = menu.findItem(R.id.action_search);

        searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        searchableInfo = searchManager.getSearchableInfo(MainActivity.this.getComponentName());

        if (searchView != null) {
            searchView.setSearchableInfo(searchableInfo);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadData(fMenu, s);
                return true;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {

                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                if(resultList!=null && wordMap!=null)
                    Toast.makeText(MainActivity.this, "Count for ["+resultList.get(i)+"] is ("+wordMap.get(resultList.get(i).toString())+")",Toast.LENGTH_LONG).show();

                searchView.setIconified(true);
                searchItem.collapseActionView();
                return false;
            }
        });


        return true;
    }

    /**
     * Need to improve the search view implementation.
     * */
    private void loadData(Menu menu, String query) {
        // Cursor
        String[] columns = new String[] { "_id", "text" };
        Object[] temp = new Object[] { 0, "default" };

        MatrixCursor cursor = new MatrixCursor(columns);
        resultList = new ArrayList<String>();
        if(wordList!=null && !wordList.isEmpty()) {
            for (int i = 0; i < wordList.size(); i++) {

                if(wordList.get(i).toLowerCase().contains(query.toLowerCase())) {
                    temp[0] = i;
                    temp[1] = wordList.get(i);//replaced s with i as s not used anywhere.
                    resultList.add(wordList.get(i));
                    cursor.addRow(temp);
                }
            }
            searchableAdapter = new ExampleAdapter(this, cursor, resultList);
            searchView.setSuggestionsAdapter(searchableAdapter);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * initing all the task objects for all the tasks.
     * */
    public void initAllTasks(){
        firstTenthElement = new TruecallerTestTask(this, new First10thResultCallback() {
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


        allTenthElements = new TruecallerTestTask(this, new All10thResultCallback() {
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


        wordCountResult = new TruecallerTestTask(this, new RepeatedWordCountCallback() {
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
                wordMap = wordCount;
                wordList = Arrays.asList(wordCount.keySet().toArray(new String[wordCount.keySet().size()]));

            }
        });
    }

    /**
     * Visual reset of the view progress bars
     * */
    public void resetProgressBars(){

    }

    /**
     * Visual reset of the fields
     * */
    public void resetResultFields(){
        request_1_result_details.setText(getResources().getString(R.string.request_1_text));
        request_2_result_details.setText(getResources().getString(R.string.request_2_text));
        request_3_result_details.setText(getResources().getString(R.string.request_3_text));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.action_search:

                return true;
            case R.id.menu_jump_result1:
                displayView(0);
                break;
            case R.id.menu_jump_result2:
                displayView(1);
                break;
            case R.id.menu_jump_result3:
                displayView(2);
                break;
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
