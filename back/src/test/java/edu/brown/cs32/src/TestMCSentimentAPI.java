package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.brown.cs32.mocks.MockSentimentJson;
import edu.brown.cs32.src.interfaces.MCInter;
import edu.brown.cs32.src.responses.utils.JSONConverter;
import edu.brown.cs32.src.sentiment.MCRequest;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import edu.brown.cs32.src.sentiment.Score;
import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestMCSentimentAPI {
  @Test
  public void testJsonConverter() throws IOException {
    SentimentJson test = JSONConverter.fromJson(MockSentimentJson.smallMockJson, SentimentJson.class);
    assertNotNull(test);
    assertEquals(test.confidence, 98);
    assertEquals(test.agreement, "AGREEMENT");
    assertEquals(test.irony, "NONIRONIC");
    assertEquals(test.score_tag, "P+");
    assertEquals(test.sentence_list.size(), 1);
    assertEquals(test.sentence_list.get(0).text, "this is really good");
    assertEquals(test.sentence_list.get(0).segment_list.size(), 1);
    assertEquals(test.sentence_list.get(0).segment_list.get(0).polarity_term_list.size(), 1);
    assertEquals(test.sentence_list.get(0).segment_list.get(0).polarity_term_list.get(0).text, "(really) good");
  }

  @Test
  public void testGetURL() {
    List<String> articles = new ArrayList<>();
    articles.add("sentence number 1 “ ” ends here.");
    articles.add("sentence two — with / symbol and = symbol and question ? symbol.");
    articles.add("sentence 3 with   weird       spaces. ] [ \"c \\ +$#@!~%^&*()_+;><");
    MCRequest tester = new MCRequest();
    String result = tester.getURL(articles);
    String expected = "https://api.meaningcloud.com/sentiment-2.1?"
        + "key=f5cb66634417b2b3b554b1d3359bc1f2&lang=en&txt="
        + "sentence%20number%201%20ends%20here.sentence%20two%20with%20symbol%20and%20symbol%20and%20question%20.symbol."
        + "sentence%203%20with%20weird%20spaces.%20c%20.%20&model=general";
    assertEquals(expected, result);

    List<String> articles2 = new ArrayList<>();
    articles2.add("This food does not taste good. Won't come back again.");
    articles2.add("The service could not have been better! But they were rude to others.");
    articles2.add("Spent too much money for food that made me hungry.");
    String result2 = tester.getURL(articles2);
    String expected2 = "https://api.meaningcloud.com/sentiment-2.1?"
        + "key=f5cb66634417b2b3b554b1d3359bc1f2&lang=en&txt="
        + "This%20food%20does%20not%20taste%20good.%20Won%20t%20come%20back%20again."
        + "The%20service%20could%20not%20have%20been%20better!%20But%20they%20were%20rude%20to%20others."
        + "Spent%20too%20much%20money%20for%20food%20that%20made%20me%20hungry.&model=general";
    assertEquals(expected2, result2);

//    String text = "WASHINGTON — Treasury Secretary Janet L. Yellen on Thursday called for a “constructive” and “healthy” economic relationship between the United States and China, one in which the two nations could work together to confront global challenges in spite of their conflicting national security interests.";
//    text = text.replaceAll("[\\p{Punct}&&[^.?!]]", " ");
//    text = text.replaceAll("— | “", " ");
//    text = text.replaceAll("\\? | !", ".");
//    text = text.replaceAll("”", " ");
//    System.out.print(text);
  }

  @Test
  public void testRanking() throws IOException {
    MCSentimentAPI tester = new MCSentimentAPI(new MCRequest());
    SentimentJson data = JSONConverter.fromJson(MockSentimentJson.bigMockJson, SentimentJson.class);
    List<Score> result = tester.calculateScores(data);
    assertEquals(result.get(0).getText(), "Spent too much money for food that made me hungry but there was a good environment if not for the mediocre view");
    assertEquals(result.get(0).getScore(), -12.5);
    assertEquals(result.get(1).getText(), "Spent the day at the park, found a lucky penny, and now I have to go home but I would rather not");
    assertEquals(result.get(1).getScore(), 6);
    assertEquals(result.get(2).getText(), "Wow this could have been so good but I wish there was more to it");
    assertEquals(result.get(2).getScore(), 8);

  }

}
