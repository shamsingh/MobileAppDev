package finalyear.coursework.coc115.mathapplication;

import android.app.Application;
import android.content.res.AssetManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 02/12/2014.
 */
public class Constants extends Application {

    public List<Question> GetAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();

        AssetManager assetManager = getResources().getAssets();

        return questionList;
    }
}
