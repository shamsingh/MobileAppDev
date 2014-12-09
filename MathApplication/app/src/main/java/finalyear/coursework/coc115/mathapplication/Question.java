package finalyear.coursework.coc115.mathapplication;

/**
 * Created by Mark on 27/11/2014.
 */
import android.content.ClipData;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
public class Question {

    private int _id;
    private String _questiontitle;
    private String _questiontext;
    private String _topicname;
    private double _variableOne;
    private double _variableTwo;
    private double _result;


    //sham- drag and drop

    private TextView option1, option2, choice1, choice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        //views to drag
        option1 = (TextView)findViewById(R.id.option_1);
        option2 = (TextView)findViewById(R.id.option_2);

        //views to drop onto
        choice1 = (TextView)findViewById(R.id.choice_1);
        choice2 = (TextView)findViewById(R.id.choice_2);

        //set touch listeners
        option1.setOnTouchListener(new ChoiceTouchListener());
        option2.setOnTouchListener(new ChoiceTouchListener());

        //set drag listeners
        choice1.setOnDragListener(new ChoiceDragListener());
        choice2.setOnDragListener(new ChoiceDragListener());

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drag_drop, menu);
        return true;
    }


    //Marks code


    public Question() {

    }

    public Question(int id, String questiontitle, String questiontext, String topicname, double variableOne, double variableTwo, double result) {
        this._id = id;
        this._questiontitle = questiontitle;
        this._questiontext = questiontext;
        this._topicname = topicname;
        this._variableOne = variableOne;
        this._variableTwo = variableTwo;
        this._result = result;
    }

    public Question(String questiontitle, String questiontext, String topicname, double variableOne, double variableTwo, double result) {
        this._questiontitle = questiontitle;
        this._questiontext = questiontext;
        this._topicname = topicname;
        this._variableOne = variableOne;
        this._variableTwo = variableTwo;
        this._result = result;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setQuestionTitle(String questiontitle) {
        this._questiontitle = questiontitle;
    }

    public String getQuestionTitle() {
        return this._questiontitle;
    }

    public void setQuestionText(String questiontext) {
        this._questiontext = questiontext;
    }

    public String getQuestionText() {
        return this._questiontext;
    }

    public void setTopicName(String topicname) {
        this._topicname = topicname;
    }

    public String getTopicName() {
        return this._topicname;
    }

    public void setVariableOne(double variableOne) { this._variableOne = variableOne; }

    public double getVariableOne() { return this._variableOne; }

    public void setVariableTwo(double variableTwo) { this._variableTwo = variableTwo; }

    public double getVariableTwo() { return this._variableTwo; }

    public void setResult(double result) {
        this._result = result;
    }

    public double getResult() {
        return this._result;
    }

}
