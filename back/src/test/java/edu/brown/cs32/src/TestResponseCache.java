package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.brown.cs32.mocks.MCMock;
import edu.brown.cs32.mocks.NYTMock;
import edu.brown.cs32.response.ServerResponse;
import edu.brown.cs32.src.news.NYTArticleAPI;
import edu.brown.cs32.src.responses.ManualResponse;
import edu.brown.cs32.src.responses.ResponseCache;
import edu.brown.cs32.src.responses.SentimentResponse;
import edu.brown.cs32.src.responses.utils.JSONConverter;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Response;
import spark.Spark;

/**
 * Class that tests the cache.
 */
public class TestResponseCache {
  private ResponseCache cache;

  /**
   * Sets up cache.
   */
  @BeforeEach
  public void setup() {
    NYTArticleAPI nyt = new NYTArticleAPI(new NYTMock());
    MCSentimentAPI sentiment = new MCSentimentAPI(new MCMock());

    this.cache = new ResponseCache(1000, 10, new ManualResponse(nyt, sentiment));
  }

  /**
   * Tests that cache loads a keyword if that keyword is not already loaded
   */
  @Test
  public void testCacheLoad() throws IOException {
    ConcurrentMap<String, String> actual = cache.getCache();
    assertEquals(actual.size(), 0);
    this.cache.getResponse("shirt");
    assertTrue(actual.containsKey("shirt"));
    assertEquals(actual.size(), 1);

    ServerResponse result = JSONConverter.fromJson(actual.get("shirt"), ServerResponse.class);
    assertEquals(result.result, "success");
    assertEquals(result.data.getSentiment(), "N");
    assertEquals(result.data.getArticles().size(), 3);
    assertEquals(result.data.getBiased().size(), 3);
  }

  /**
   * Tests that cache does not load a keyword again if that keyword is already loaded
   */
  @Test
  public void testCacheLoadTwice() throws IOException {
    this.cache.getResponse("shirt");
    this.cache.getResponse("shirt");
    ConcurrentMap<String, String> actual = cache.getCache();
    assertTrue(actual.containsKey("shirt"));
    assertEquals(actual.size(), 1);

    ServerResponse result = JSONConverter.fromJson(actual.get("shirt"), ServerResponse.class);
    assertEquals(result.result, "success");
    assertEquals(result.data.getSentiment(), "N");
    assertEquals(result.data.getArticles().size(), 3);
    assertEquals(result.data.getBiased().size(), 3);
  }

  /**
   * Tests that cache does not load a keyword again if that keyword is already loaded
   */
  @Test
  public void testCacheLoadMulti() throws IOException {
    this.cache.getResponse("shirt");
    this.cache.getResponse("china");
    ConcurrentMap<String, String> actual = cache.getCache();
    assertTrue(actual.containsKey("shirt"));
    assertEquals(actual.size(), 2);

    ServerResponse result = JSONConverter.fromJson(actual.get("shirt"), ServerResponse.class);
    assertEquals(result.result, "success");
    assertEquals(result.data.getSentiment(), "N");
    assertEquals(result.data.getArticles().size(), 3);
    assertEquals(result.data.getBiased().size(), 3);

    ServerResponse result2 = JSONConverter.fromJson(actual.get("china"), ServerResponse.class);
    assertEquals(result2.result, "success");
    assertEquals(result2.data.getSentiment(), "P");
    assertEquals(result2.data.getArticles().size(), 2);
    assertEquals(result2.data.getBiased().size(), 2);
  }

  /**
   * Tests that nothing gets added to cache if cache size is 0 or if time limit is 0
   */
  @Test
  public void testCacheSizeLimit() {
    NYTArticleAPI nyt = new NYTArticleAPI(new NYTMock());
    MCSentimentAPI sentiment = new MCSentimentAPI(new MCMock());

    this.cache = new ResponseCache(1000, 0, new ManualResponse(nyt, sentiment));
    this.cache.getResponse("shirt");
    ConcurrentMap<String, String> actual = cache.getCache();
    assertFalse(actual.containsKey("shirt"));
    assertEquals(actual.size(), 0);

    this.cache = new ResponseCache(0, 10, new ManualResponse(nyt, sentiment));
    this.cache.getResponse("shirt");
    actual = cache.getCache();
    assertFalse(actual.containsKey("shirt"));
    assertEquals(actual.size(), 0);
  }
}
