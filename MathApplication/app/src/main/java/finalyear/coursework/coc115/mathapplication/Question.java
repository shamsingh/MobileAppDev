package finalyear.coursework.coc115.mathapplication;

/**
 * Created by Mark on 27/11/2014.
 */

public class Question {

    private int _id;
    private String _questiontitle;
    private String _questiontext;
    private String _topicname;
    private double _variableOne;
    private double _variableTwo;
    private double _result;

    public Question() {
        // empty constructor
    }

    public Question(int id, String questiontitle, String questiontext, String topicname, double variableOne, double variableTwo, double result) {
        //create question with id
        this._id = id;
        this._questiontitle = questiontitle;
        this._questiontext = questiontext;
        this._topicname = topicname;
        this._variableOne = variableOne;
        this._variableTwo = variableTwo;
        this._result = result;
    }

    public Question(String questiontitle, String questiontext, String topicname, double variableOne, double variableTwo, double result) {
        //create question without id
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
