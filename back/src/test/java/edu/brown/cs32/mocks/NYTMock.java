package edu.brown.cs32.mocks;

import edu.brown.cs32.src.interfaces.NYTInter;
import edu.brown.cs32.mocks.MockArticles;
import java.util.Map;
import java.util.HashMap;

public class NYTMock implements NYTInter {
  private Map<String, Map<String, Object>> queries = new HashMap<String, Map<String, Object>>(){{
    put("korea", MockArticles.fullNYTResponse());
  }};

  /**
   * Returns mocked API request for testing purposes.
   * @param keyword inputted keyword
   * @return map of API request results.
   */
  @Override
  public Map<String, Object> apiRequest(String keyword) {
    Map<String, Object> results;
    try{
      results = queries.get(keyword);
    }
    catch(NullPointerException e){
      return null;
    }
    return results;
  }
}