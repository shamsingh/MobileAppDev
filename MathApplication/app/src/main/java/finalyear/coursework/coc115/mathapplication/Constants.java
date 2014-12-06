package finalyear.coursework.coc115.mathapplication;

import android.app.Application;

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

    public List<Question> GetAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();

        try {
            InputStream iS = getResources().getAssets().open("questions.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(iS);

            dom.getDocumentElement().normalize();

            NodeList topicList = dom.getElementsByTagName("topic");

            for(int temp = 0; temp < topicList.getLength(); temp++) {
                Node nTopic = topicList.item(temp);

                if(nTopic.getNodeType() == Node.ELEMENT_NODE) {

                    Element eTopic = (Element)nTopic;
                    NodeList questionArray = eTopic.getElementsByTagName("question");
                    for(int i = 0; i < questionArray.getLength(); i++) {

                        Node nQuestion = questionArray.item(i);

                        if(nQuestion.getNodeType() == Node.ELEMENT_NODE) {

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
