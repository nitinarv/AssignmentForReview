package res.truecaller.sample.com.truecallersample.view;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import res.truecaller.sample.com.truecallersample.R;


public class MainActivity extends AppCompatActivity implements TaskFragment.TaskCallbacks{

    Button request_button;

    private Toolbar mToolbar;

    private List<String> wordList;
    private List<String> resultList;
    private HashMap<String, Integer> wordMap;
    private boolean mSearchOpened;
    private SearchManager searchManager;
    private SearchView searchView;
    private SearchableInfo searchableInfo;
    private ExampleAdapter searchableAdapter;
    private MenuItem searchItem;
    private TaskFragment taskFragment;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        request_button = (Button) findViewById(R.id.request_button);

        taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentByTag(TaskFragment.TAG_TASK_FRAGMENT);
        frameLayout = (FrameLayout) findViewById(R.id.tasks_fragment);

        if (savedInstanceState == null) {
            if(taskFragment==null) {
                taskFragment = new TaskFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tasks_fragment, taskFragment, TaskFragment.TAG_TASK_FRAGMENT)
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.bounce_interpolator)
                    .commit();
        }else{
            taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentByTag(TaskFragment.TAG_TASK_FRAGMENT);
        }

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_button.setEnabled(false);
                wordList = null;
                taskFragment.runAllTasks();
            }
        });

        mSearchOpened = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final Menu finalMenu = menu;
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
                loadData(finalMenu, s);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.action_search:

                return true;
            case R.id.menu_jump_result1:
                taskFragment.displayView(0);
                break;
            case R.id.menu_jump_result2:
                taskFragment.displayView(1);
                break;
            case R.id.menu_jump_result3:
                taskFragment.displayView(2);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void enableButton() {
        if(request_button!=null){
            request_button.setEnabled(true);
        }
    }

    @Override
    public void setWordData(HashMap<String, Integer> mWordMap) {
        wordMap = mWordMap;
        wordList = Arrays.asList(wordMap.keySet().toArray(new String[wordMap.keySet().size()]));
    }
}
