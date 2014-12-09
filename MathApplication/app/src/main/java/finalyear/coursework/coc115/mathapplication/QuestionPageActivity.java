package finalyear.coursework.coc115.mathapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class QuestionPageActivity extends ActionBarActivity {

    private TextView variable1, variable2, variable3, variable4, choice1, choice2;

    private Question question;

    String chosenVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        //get the question from the given ID
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        question = dbHandler.findQuestion(getIntent().getIntExtra("questionID", -1));

        //views to drag
        variable1 = (TextView)findViewById(R.id.variable_1);
        variable2 = (TextView)findViewById(R.id.variable_2);
        variable3 = (TextView)findViewById(R.id.variable_3);
        variable4 = (TextView)findViewById(R.id.variable_4);

        //set touch listeners
        variable1.setOnTouchListener(new ChoiceTouchListener());
        variable2.setOnTouchListener(new ChoiceTouchListener());
        variable3.setOnTouchListener(new ChoiceTouchListener());
        variable4.setOnTouchListener(new ChoiceTouchListener());

        ((TextView)findViewById(R.id.text)).setText(question.getQuestionText());

        //set the variable values
        if(question.getQuestionTitle().contains("Hard")) {
            //this is a hard question
            Double missingVar = question.getVariableOne();
            chosenVar = Integer.toString((int)Math.round(missingVar));
            String[] arrayls = new String[] { Integer.toString((int) Math.round(missingVar)), Integer.toString((int) Math.round(missingVar * 2)), Integer.toString((int)Math.round(missingVar - 2)), Integer.toString((int)Math.round(missingVar + 3)) };

            variable1.setText(arrayls[3]);
            variable2.setText(arrayls[0]);
            variable3.setText(arrayls[1]);
            variable4.setText(arrayls[2]);

            //views to drop onto
            choice1 = (TextView)findViewById(R.id.choice_1);
            //set drag listeners
            choice1.setOnDragListener(new ChoiceDragListener());

            //set other variables
            choice2 = (TextView)findViewById(R.id.operation_1);
            String operation;
            String topicName = question.getTopicName();
            if(topicName.equals("addition")) {
                operation = "+";
            } else if(topicName.equals("subtraction")) {
                operation = "-";
            } else if(topicName.equals("multiplication")) {
                operation = "\u00D7";
            } else {
                operation = "\u00F7";
            }

            choice2.setText(operation);

            choice2 = (TextView)findViewById(R.id.choice_2);
            choice2.setText(Integer.toString((int) Math.round(question.getVariableTwo())));

            choice2 = (TextView)findViewById(R.id.result);
            choice2.setText(Integer.toString((int) Math.round(question.getResult())));
        } else {
            //this is an easy question
            Double missingVar = question.getResult();
            chosenVar = Integer.toString((int)Math.round(missingVar));
            String[] arrayls = new String[] { Integer.toString((int) Math.round(missingVar)), Integer.toString((int) Math.round(missingVar * 2)), Integer.toString((int)Math.round(missingVar - 2)), Integer.toString((int)Math.round(missingVar + 3)) };

            variable1.setText(arrayls[2]);
            variable2.setText(arrayls[3]);
            variable3.setText(arrayls[0]);
            variable4.setText(arrayls[1]);

            //views to drop onto
            choice1 = (TextView)findViewById(R.id.result);
            //set drag listeners
            choice1.setOnDragListener(new ChoiceDragListener());

            //set other variables
            choice2 = (TextView)findViewById(R.id.operation_1);
            String operation;
            String topicName = question.getTopicName();
            if(topicName.equals("addition")) {
                operation = "+";
            } else if(topicName.equals("subtraction")) {
                operation = "-";
            } else if(topicName.equals("multiplication")) {
                operation = "\u00D7";
            } else {
                operation = "\u00F7";
            }

            choice2.setText(operation);

            choice2 = (TextView)findViewById(R.id.choice_2);
            choice2.setText(Integer.toString((int) Math.round(question.getVariableTwo())));

            choice2 = (TextView)findViewById(R.id.choice_1);
            choice2.setText(Integer.toString((int) Math.round(question.getVariableOne())));
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) dragEvent.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.INVISIBLE);
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if(tag!=null)
                    {
                        //the tag is the view id already dropped here
                        int existingID = (Integer)tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
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

    public void onSubmit(View view) {
        if(choice1.getText().equals("?")) {
            Toast.makeText(getApplicationContext(), "Please enter your choice!", Toast.LENGTH_LONG).show();
        } else {
            //check answer
            Intent newIntent = new Intent(getApplicationContext(), ResultsPageActivity.class);
            if(choice1.getText().equals(chosenVar)) {
                newIntent.putExtra("result", true);
            } else {
                newIntent.putExtra("result", false);
            }
            startActivity(newIntent);
        }
    }
}
