package finalyear.coursework.coc115.mathapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class TopicsPageActivity extends ActionBarActivity
        implements topicFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_page);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topics_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        changeActionBar(config);
    }

    private void changeActionBar(Configuration config) {
        ActionBar actionBar = getSupportActionBar();
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.hide();
            Toast.makeText(getApplicationContext(), "Tis Landscape", Toast.LENGTH_SHORT).show();
        }
        else if(config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            actionBar.show();
            Toast.makeText(getApplicationContext(), "Tis Portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void topicButton_Click(View view) {
        String name = "";
        switch(view.getId()) {
            case R.id.addition_button:
                name = "addition";
                break;
            case R.id.subtraction_button:
                name = "subtraction";
                break;
            case R.id.multiplication_button:
                name = "multiplication";
                break;
            case R.id.division_button:
                name = "division";
                break;
        }

        Intent newIntent = new Intent(this, finalyear.coursework.coc115.mathapplication.QuestionListActivity.class);
        newIntent.putExtra("topicChosen", name);
        startActivity(newIntent);
    }
}
