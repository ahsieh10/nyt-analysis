package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.*;

import com.squareup.moshi.Moshi;
import edu.brown.cs32.mocks.MCMock;
import edu.brown.cs32.mocks.MockSentimentJson;
import edu.brown.cs32.mocks.NYTMock;
import edu.brown.cs32.response.ServerResponse;
import edu.brown.cs32.src.news.NYTArticleAPI;
import edu.brown.cs32.src.news.NYTRequest;
import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.responses.ManualResponse;
import edu.brown.cs32.src.responses.ResponseCache;
import edu.brown.cs32.src.responses.SentimentResponse;
import edu.brown.cs32.src.sentiment.MCRequest;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

/**
 * This is the class that tests that the API server works properly. It checks that the server can
 * handle requests to search a word and get article and sentiment data.
 */
public class TestSearchHandler {

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  /**
   * Restarts server before each test.
   */
  @BeforeEach
  public void setup() {
    NYTArticleAPI nyt = new NYTArticleAPI(new NYTMock());
    MCSentimentAPI sentiment = new MCSentimentAPI(new MCMock());

    ResponseCache query = new ResponseCache(1000, 10, new ManualResponse(nyt, sentiment));
    Spark.get("/search", new SearchHandler(query));
    Spark.init();
    Spark.awaitInitialization();
  }

  /**
   * Stops server listening after each test.
   */
  @AfterEach
  public void teardown() {
    Spark.unmap("/search");
    Spark.awaitStop();
  }

  /**
   * Helper to start a connection to a specific API endpoint/params
   * @param apiCall the call string, including endpoint
   *                (NOTE: this would be better if it had more structure!)
   * @return the connection for the given URL, just after connecting
   * @throws IOException if the connection fails for some reason
   */
  private HttpURLConnection tryRequest(String apiCall) throws IOException {
    URL requestURL = new URL("http://localhost:"+Spark.port()+"/"+apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();

    clientConnection.connect();
    return clientConnection;
  }

  /**
   * Helper to get the response from the connection's input stream and return it as the specified class
   * @return - the object representing the response to the request
   * @throws IOException if failed to read from input stream
   */
  private <T> T getResponse(String apiCall, Class<T> responseClass) throws IOException {
    HttpURLConnection clientConnection = tryRequest(apiCall);
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    T map = moshi.adapter(responseClass).fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    clientConnection.disconnect();
    return map;
  }

  /**
   * Tests the response for a good search
   *
   * @throws IOException if the connection fails for some reason
   */
  @Test
  public void testBasicSearch() throws IOException {
    ServerResponse result = getResponse("search?keyword=shirt", ServerResponse.class);

    assertEquals(result.result, "success");
    SentimentResponse sentResult = result.data;
    assertNotNull(sentResult);
    assertEquals(sentResult.getSentiment(), "N");
    assertEquals(sentResult.getBiased(), List.of("Shirt fits weird.", "The shirt costs too much for the quality.", "My shirt shrunk."));
    assertEquals(sentResult.getArticles().get(0).getAbstract(), "a1");
    assertEquals(sentResult.getArticles().get(1).getAbstract(), "a2");
    assertEquals(sentResult.getArticles().get(2).getAbstract(), "a3");
    assertEquals(sentResult.getArticles().size(), 3);
  }

  /**
   * Tests multiple searches to see if data updates properly
   *
   * @throws IOException if the connection fails for some reason
   */
  @Test
  public void testMultiSearch() throws IOException {
    ServerResponse result = getResponse("search?keyword=shirt", ServerResponse.class);
    ServerResponse result2 = getResponse("search?keyword=china", ServerResponse.class);

    assertEquals(result.result, "success");
    SentimentResponse sentResult = result.data;
    assertNotNull(sentResult);
    assertEquals(sentResult.getSentiment(), "N");
    assertEquals(sentResult.getBiased(), List.of("Shirt fits weird.", "The shirt costs too much for the quality.", "My shirt shrunk."));
    assertEquals(sentResult.getArticles().get(0).getAbstract(), "a1");
    assertEquals(sentResult.getArticles().get(1).getAbstract(), "a2");
    assertEquals(sentResult.getArticles().get(2).getAbstract(), "a3");
    assertEquals(sentResult.getArticles().size(), 3);

    assertEquals(result2.result, "success");
    SentimentResponse sentResult2 = result2.data;
    assertNotNull(sentResult2);
    assertEquals(sentResult2.getSentiment(), "P");
    assertEquals(sentResult2.getBiased(), List.of(MockSentimentJson.NYTSegment2, MockSentimentJson.NYTSegment1));
    assertEquals(sentResult2.getArticles().get(0).getUrl(), "https://www.nytimes.com/2023/04/27/business/china-banker-crackdown.html");
    assertEquals(sentResult2.getArticles().get(1).getUrl(), "https://www.nytimes.com/2023/04/26/world/asia/china-taiwan-publisher-detained.html");
    assertEquals(sentResult2.getArticles().size(), 2);
  }

  /**
   * Tests the response for a search missing a keyword
   *
   * @throws IOException if the connection fails for some reason
   */
  @Test
  public void testBadSearch() throws IOException {
    Map<String, Object> result = getResponse("search?", Map.class);

    assertEquals(result.get("result"), "error_bad_request");
    assertEquals(result.get("error_message"), "missing parameters. Make sure to include: keyword");
  }

  /**
   * Tests the response for a search that would not produce article results
   *
   * @throws IOException if the connection fails for some reason
   */
  @Test
  public void testErrorSearch() throws IOException {
    Map<String, Object> result = getResponse("search?keyword=noresult", Map.class);

    assertEquals(result.get("status"), "error_bad_json");
    assertEquals(result.get("error_message"), "Invalid input.");
  }

    /**
   * Fuzz test to make sure server does not crash given random keywords
   * @throws IOException
   */
  @Test
  public void testFuzz() throws IOException{

    String randomString = "search?keyword=";;

    for(int i = 0; i < 1000; i++) {

      String randomWord = getRandomStringBounded(6, 45, 126);
      randomString += randomWord;

      HttpURLConnection clientConnection = this.tryRequest(randomString);
      if (clientConnection.getResponseCode() >= 400 && clientConnection.getResponseCode() < 500) {
        System.out.println("Response Code:" + clientConnection.getResponseCode());
      } else if (clientConnection.getResponseCode() > 500) {
        System.out.println("Server Error Response Code:" + clientConnection.getResponseCode());
      }

      assertEquals(200, clientConnection.getResponseCode());
      clientConnection.disconnect();
    }
  }

  /**
   * Method below from: https://github.com/cs0320-s2023/csv-tnelson.git
   * Helper methods that generates random strings but does not guarantee an alpha-numeric string
   * @param length The length of the string to generate
   * @param first the first ASCII-code in the character range allowed
   * @param last the last ASCII-code in the character range allowed
   * @return a random string of length bytes
   */
  private static String getRandomStringBounded(int length, int first, int last) {
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    StringBuilder sb = new StringBuilder();
    for(int iCount=0;iCount<length;iCount++) {
      // upper-bound is exclusive
      int code = r.nextInt(first, last+1);
      sb.append((char) code);
    }
    return sb.toString();
  }

}
