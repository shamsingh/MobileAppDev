package finalyear.coursework.coc115.mathapplication;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mark on 02/12/2014.
 */
public class Constants extends Application {

    public static List<Question> GetAllQuestions(Context context) {
        List<Question> questionList = new ArrayList<Question>();

        //attempt to get resources
        try {
            Resources resources = context.getResources();
            AssetManager assets = resources.getAssets();
            //open file
            InputStream iS = assets.open("questions.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(iS);

            dom.getDocumentElement().normalize();

            //read from file
            NodeList topicList = dom.getElementsByTagName("topic");

            //foreach topic:
            for(int temp = 0; temp < topicList.getLength(); temp++) {
                Node nTopic = topicList.item(temp);

                if(nTopic.getNodeType() == Node.ELEMENT_NODE) {

                    //foreach question:
                    Element eTopic = (Element)nTopic;
                    NodeList questionArray = eTopic.getElementsByTagName("question");
                    for(int i = 0; i < questionArray.getLength(); i++) {

                        Node nQuestion = questionArray.item(i);

                        if(nQuestion.getNodeType() == Node.ELEMENT_NODE) {

                            //set question variables
                            Element eQuestion = (Element)nQuestion;
                            Question newQuestion = new Question();
                            newQuestion.setTopicName(eTopic.getAttribute("id"));
                            newQuestion.setQuestionTitle(eQuestion.getElementsByTagName("title").item(0).getTextContent());
                            newQuestion.setQuestionText(eQuestion.getElementsByTagName("text").item(0).getTextContent());
                            newQuestion.setVariableOne(Double.parseDouble(eQuestion.getElementsByTagName("variable").item(0).getTextContent()));
                            newQuestion.setVariableTwo(Double.parseDouble(eQuestion.getElementsByTagName("variable").item(1).getTextContent()));
                            newQuestion.setResult(Double.parseDouble(eQuestion.getElementsByTagName("result").item(0).getTextContent()));

                            questionList.add(newQuestion);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return questionList;
    }
}
