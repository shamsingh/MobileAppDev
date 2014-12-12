package finalyear.coursework.coc115.mathapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class TopicsPageActivity extends ActionBarActivity
        implements topicFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_topic);
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

        Intent newIntent = new Intent(getApplicationContext(), QuestionListActivity.class);
        newIntent.putExtra("topicChosen", name);
        startActivity(newIntent);
    }
}
