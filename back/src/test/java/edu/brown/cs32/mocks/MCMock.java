package edu.brown.cs32.mocks;

import edu.brown.cs32.src.interfaces.MCInter;
import edu.brown.cs32.src.responses.utils.JSONConverter;
import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that uses mocks instead of calling MC API
 */
public class MCMock implements MCInter {
  private Map<String, String> articleSentiment = new HashMap<>(){{
    put(MockSentimentJson.NYTSentence, MockSentimentJson.intMockJson);
    put(MockSentimentJson.NYTText, MockSentimentJson.NYTMockJson);
  }};

  /**
   * Returns mocked sentiment json based on article input
   * @param articles lead paragraphs from list of articles
   * @return mocked sentinent json
   * @throws IOException
   */
  @Override
  public SentimentJson apiRequest(List<String> articles) throws IOException {
    String results;
      String text = "";
      for (String article : articles) {
        text += article;
      }
      results = articleSentiment.get(text);
    return JSONConverter.fromJson(results, SentimentJson.class);
  }
}