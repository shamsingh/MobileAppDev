package finalyear.coursework.coc115.mathapplication;

/**
 * Created by Mark on 27/11/2014.
 */
public class Question {

    private int _id;
    private String _questiontitle;
    private String _questiontext;

    public Question() {

    }

    public Question(int id, String questiontitle, String questiontext) {
        this._id = id;
        this._questiontitle = questiontitle;
        this._questiontext = questiontext;
    }

    public Question(String questiontitle, String questiontext) {
        this._questiontitle = questiontitle;
        this._questiontext = questiontext;
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
}
