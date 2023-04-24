package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.brown.cs32.mocks.MockSentimentJson;
import edu.brown.cs32.src.responses.JSONConverter;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
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
    articles.add("sentence number 1 ends here.");
    articles.add("sentence two with / symbol and = symbol and question ? symbol.");
    articles.add("sentence 3 with   weird       spaces. ] [ \" \\ +$#@!~%^&*()_+;><");
    MCSentimentAPI Tester = new MCSentimentAPI();
    String result = Tester.getURL(articles);
    String expected = "https://api.meaningcloud.com/sentiment-2.1?"
        + "key=f5cb66634417b2b3b554b1d3359bc1f2&lang=en&txt="
        + "sentence%20number%201%20ends%20here.sentence%20two%20with%20symbol%20and%20symbol%20and%20question%20.symbol."
        + "sentence%203%20with%20weird%20spaces.%20.%20&model=general";
    assertEquals(expected, result);

    List<String> articles2 = new ArrayList<>();
    articles2.add("This food does not taste good. Won't come back again.");
    articles2.add("The service could not have been better! But they were rude to others.");
    articles2.add("Spent too much money for food that made me hungry.");
    String result2 = Tester.getURL(articles2);
    String expected2 = "https://api.meaningcloud.com/sentiment-2.1?"
        + "key=f5cb66634417b2b3b554b1d3359bc1f2&lang=en&txt="
        + "This%20food%20does%20not%20taste%20good.%20Won%20t%20come%20back%20again."
        + "The%20service%20could%20not%20have%20been%20better!%20But%20they%20were%20rude%20to%20others."
        + "Spent%20too%20much%20money%20for%20food%20that%20made%20me%20hungry.&model=general";
    assertEquals(expected2, result2);
  }

}
