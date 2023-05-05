package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.brown.cs32.mocks.MCMock;
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

/**
 * Tests for MCSentiment functions.
 */
public class TestMCSentimentAPI {

  /**
   * Tests that JSONConverter can convert a json from MC into a SentimentJson object
   * @throws IOException
   */
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

  /**
   * Tests that get sentiment can properly get the score tag from a mocked sentiment json
   * @throws IOException
   */
  @Test
  public void testGetSentiment() throws IOException {
    MCSentimentAPI tester = new MCSentimentAPI(new MCMock());
    List<String> sentences = new ArrayList<>();
    sentences.add(MockSentimentJson.NYTSentence);
    String sentiment = tester.getSentiment(sentences);
    assertEquals("P", sentiment);

    List<String> sentences2 = new ArrayList<>();
    sentences2.add("Shirt fits weird.");
    sentences2.add("My shirt shrunk.");
    sentences2.add("The shirt costs too much for the quality.");
    String sentiment2 = tester.getSentiment(sentences2);
    assertEquals("N", sentiment2);

  }

  /**
   * Tests that the url for the MC API request is properly formatted (punctuation doesn't break the url)
   */
  @Test
  public void testGetURL() {
    List<String> articles = new ArrayList<>();
    articles.add("sentence | ' number 1 h“ ” ends here.");
    articles.add("sentence two — with / symbol and = symbol and question ? symbol.");
    articles.add("sentence 3 with   weird       spaces. ] [ \"c \\ +$#@!~%^&*()_+;><");
    MCRequest tester = new MCRequest();
    String result = tester.getURL(articles);
    String expected = "https://api.meaningcloud.com/sentiment-2.1?"
        + "key=f5cb66634417b2b3b554b1d3359bc1f2&lang=en&txt="
        + "sentence%20number%201%20h%20ends%20here.sentence%20two%20with%20symbol%20and%20symbol%20and%20question%20.%20symbol."
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
        + "The%20service%20could%20not%20have%20been%20better.%20But%20they%20were%20rude%20to%20others."
        + "Spent%20too%20much%20money%20for%20food%20that%20made%20me%20hungry.&model=general";
    assertEquals(expected2, result2);

//    String text = "WASHINGTON — Treasury Secretary Janet L. Yellen on Thursday called for a “constructive” and “healthy” economic relationship between the United States and China, one in which the two nations could work together to confront global challenges in spite of their conflicting national security interests.";
//    text = text.replaceAll("[\\p{Punct}&&[^.?!]]", " ");
//    text = text.replaceAll("— | “", " ");
//    text = text.replaceAll("\\? | !", ".");
//    text = text.replaceAll("”", " ");
//    System.out.print(text);
  }

  /**
   * Tests that the ranking method properly scores and ranks sentences based on confidence,
   * number of polarity terms, and individual score tags
   * @throws IOException
   */
  @Test
  public void testRanking() throws IOException {
    MCSentimentAPI tester = new MCSentimentAPI(new MCMock());
    List<String> sentences = new ArrayList<>();
    sentences.add(MockSentimentJson.NYTSentence);
    tester.getSentiment(sentences);
    SentimentJson data = JSONConverter.fromJson(MockSentimentJson.bigMockJson, SentimentJson.class);
    List<Score> result = tester.calculateScores(data);
    assertEquals(result.get(2).getText(), "Spent too much money for food that made me hungry but there was a good environment if not for the mediocre view");
    assertEquals(result.get(2).getScore(), -12.5);
    assertEquals(result.get(1).getText(), "Spent the day at the park, found a lucky penny, and now I have to go home but I would rather not");
    assertEquals(result.get(1).getScore(), 6);
    assertEquals(result.get(0).getText(), "Wow this could have been so good but I wish there was more to it");
    assertEquals(result.get(0).getScore(), 8);

    List<String> sentences2 = new ArrayList<>();
    sentences2.add("Shirt fits weird.");
    sentences2.add("My shirt shrunk.");
    sentences2.add("The shirt costs too much for the quality.");
    tester.getSentiment(sentences2);
    SentimentJson data2 = JSONConverter.fromJson(MockSentimentJson.NYTMockJson, SentimentJson.class);
    List<Score> result2 = tester.calculateScores(data2);

    assertEquals(result2.get(0).getText(), "Shirt fits weird.");
    assertEquals(result2.get(0).getScore(), -7.0);
    assertEquals(result2.get(1).getText(), "The shirt costs too much for the quality.");
    assertEquals(result2.get(1).getScore(), -6.0);
    assertEquals(result2.get(2).getText(), "My shirt shrunk.");
    assertEquals(result2.get(2).getScore(), 0.0);
  }

  /**
   * Tests that the filtering method gets rid of incomplete sentences and sentences with sentiment
   * scores opposite to the overall sentiment
   * @throws IOException
   */
  @Test
  public void testFiltering() throws IOException {
    MCSentimentAPI tester = new MCSentimentAPI(new MCMock());
    List<String> sentences = new ArrayList<>();
    sentences.add(MockSentimentJson.NYTSentence);
    tester.getSentiment(sentences);

    List<Score> data = new ArrayList<>();
    data.add(new Score("Opposite sentiment.", -10.0));
    data.add(new Score("Valid score.", 5.0));
    data.add(new Score("lower case sentence.", 10.0));
    data.add(new Score("No punctuation", 20.0));
    data.add(new Score("More positive.", 20.0));
    List<String> result = tester.filterSentences(data);

    assertEquals(result.get(0), "Valid score.");
    assertEquals(result.get(1), "More positive.");
    assertEquals(result.size(), 2);
  }

}
