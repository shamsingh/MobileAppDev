package finalyear.coursework.coc115.mathapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Mark on 27/11/2014.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mathappDB.db";
    private static final String TABLE_QUESTIONS = "questions";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_QUESTIONTITLE = "questiontitle";
    private static final String COLUMN_QUESTIONTEXT = "questiontext";
    private static final String COLUMN_TOPICNAME = "topicname";
    private static final String COLUMN_VARIABLEONE = "variableone";
    private static final String COLUMN_VARIABLETWO = "variabletwo";
    private static final String COLUMN_RESULT = "result";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " +
                                    TABLE_QUESTIONS + "(" +
                                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                                    COLUMN_QUESTIONTITLE + " TEXT," +
                                    COLUMN_QUESTIONTEXT + " TEXT" +
                                    COLUMN_TOPICNAME + " TEXT" +
                                    COLUMN_VARIABLEONE + " TEXT" +
                                    COLUMN_VARIABLETWO + " TEXT" +
                                    COLUMN_RESULT + " TEXT" + ")";
        db.execSQL(CREATE_QUESTION_TABLE);

        List<Question> questionList = new Constants().GetAllQuestions();

        for(Question question : questionList) {
            addQuestion(question);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTIONTITLE, question.getQuestionTitle());
        values.put(COLUMN_QUESTIONTEXT, question.getQuestionText());
        values.put(COLUMN_TOPICNAME, question.getTopicName());
        values.put(COLUMN_VARIABLEONE, String.valueOf(question.getVariableOne()));
        values.put(COLUMN_VARIABLETWO, String.valueOf(question.getVariableTwo()));
        values.put(COLUMN_RESULT, String.valueOf(question.getResult()));

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public Question findQuestion(int questionID) {
        String query = "Select * FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_ID + " = \"" + questionID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Question question = new Question();

        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            question.setID(Integer.parseInt(cursor.getString(0)));
            question.setQuestionTitle(cursor.getString(1));
            question.setQuestionText(cursor.getString(2));
            question.setTopicName(cursor.getString(3));
            question.setVariableOne(Double.valueOf(cursor.getString(4)));
            question.setVariableTwo(Double.valueOf(cursor.getString(5)));
            question.setResult(Double.valueOf(cursor.getString(6)));
            cursor.close();
        } else {
            question = null;
        }
        db.close();
        return question;
    }

    public boolean deleteQuestion(int questionID) {
        boolean result = false;

        String query = "Select * FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_ID + " = \"" + questionID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Question question = new Question();

        if(cursor.moveToFirst()) {
            question.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_QUESTIONS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(question.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
