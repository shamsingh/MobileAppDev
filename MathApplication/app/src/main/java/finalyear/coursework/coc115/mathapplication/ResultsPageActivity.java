package finalyear.coursework.coc115.mathapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;


public class ResultsPageActivity extends ActionBarActivity {

    private UiLifecycleHelper uiHelper;
    private Question question;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra("result", false)) {
            setContentView(R.layout.activity_results_correct_page);
        } else {
            setContentView(R.layout.activity_results_wrong_page);
        }

        //get the question from the given ID
        dbHandler = new MyDBHandler(this, null, null, 1);
        question = dbHandler.findQuestion(getIntent().getIntExtra("questionID", -1));

        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    public void shareToFacebook(View v) {
        if(FacebookDialog.canPresentShareDialog(getApplicationContext(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            //Publish post using share builder
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setLink("http://facebook.com")
                    .build();

            uiHelper.trackPendingDialogCall((shareDialog.present()));
        } else {
            //Fallback, using the Feed dialog
            //publishFeedDialog();
            Toast.makeText(getApplicationContext(), "no facebook installed :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void publishFeedDialog() {
        //Make new bundle
        Bundle params = new Bundle();
        params.putString("name", "Teadia");
        params.putString("caption", "Try and beat this score!");
        params.putString("description", "You did well!");
        params.putString("link", "https://github.com/shamsingh/MobileAppDev");
        params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

        //create new web dialog
        WebDialog feedDialog = (
                new WebDialog.FeedDialogBuilder(getApplicationContext(),
                        Session.getActiveSession(),
                        params))
                .setOnCompleteListener(new WebDialog.OnCompleteListener() {

                    @Override
                    public void onComplete(Bundle values,
                                           FacebookException error) {
                        if (error == null) {
                            // When the story is posted, echo the success
                            // and the post Id.
                            final String postId = values.getString("post_id");
                            if (postId != null) {
                                Toast.makeText(getApplicationContext(),
                                        "Posted story, id: " + postId,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // User clicked the Cancel button
                                Toast.makeText(getApplicationContext().getApplicationContext(),
                                        "Publish cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error instanceof FacebookOperationCanceledException) {
                            // User clicked the "x" button
                            Toast.makeText(getApplicationContext().getApplicationContext(),
                                    "Publish cancelled",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Generic, ex: network error
                            Toast.makeText(getApplicationContext().getApplicationContext(),
                                    "Error posting story",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                })
                .build();
        //show web dialog
        feedDialog.show();
    }

    public void nextQuestion(View v) {
        //get the next question ID
        Question nextQuestion = dbHandler.findQuestion(question.getID()+1);

        //find if next question exists
        if(nextQuestion == null) {
            //Go to topics page
            Toast.makeText(this, "No next question found", Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(this, TopicsPageActivity.class);
            startActivity(newIntent);
        } else {
            //Go to next question
            Intent newIntent = new Intent(this, QuestionPageActivity.class);
            newIntent.putExtra("questionID", nextQuestion.getID());
            startActivity(newIntent);
        }
    }

    public void replayQuestion(View v) {
        //go back to question page
        Intent newIntent = new Intent(this, QuestionPageActivity.class);
        newIntent.putExtra("questionID", question.getID());
        startActivity(newIntent);
    }

    public void menuButton(View v) {
        Intent intent = new Intent(this, TopicsPageActivity.class);
        startActivity(intent);
    }

}
