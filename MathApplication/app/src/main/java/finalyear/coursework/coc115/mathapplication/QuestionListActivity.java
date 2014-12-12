package finalyear.coursework.coc115.mathapplication;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import com.commonsware.cwac.loaderex.SQLiteCursorLoader;

import java.lang.reflect.Type;


public class QuestionListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private int LIST_ID = 0;
    private String TOPIC = "";

    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        TOPIC = getIntent().getStringExtra("topicChosen");

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {"questiontitle", "questiontext"};
        int[] toViews = {R.id.textTitle, R.id.textText}; // The TextView in simple_list_item_1

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.questionlistitem, null,
                fromColumns, toViews, 0);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) mAdapter.getItem(position);
                int ID = (int) cursor.getLong(cursor.getColumnIndex("_id"));

                Intent newIntent = new Intent(getApplicationContext(), QuestionPageActivity.class);
                newIntent.putExtra("questionID", ID);
                startActivity(newIntent);
            }
        });

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(LIST_ID, null, this);
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String rawQuery = "Select * FROM questions WHERE topicname = \"" + TOPIC + "\"";
        String[] params = null;
        return new SQLiteCursorLoader(
                getApplicationContext(),
                new MyDBHandler(getApplicationContext(), null, null, 1),
                rawQuery,
                params
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
