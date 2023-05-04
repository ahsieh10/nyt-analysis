package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.NYTInter;
import edu.brown.cs32.src.privatekey.Keys;
import edu.brown.cs32.src.responses.utils.RequestUtil;
import java.io.IOException;
import java.util.Map;

public class NYTRequest implements NYTInter {

  /**
   * Empty constructor
   */
  public NYTRequest(){};

  /**
   * Makes a real request to the NYT API and returns its results in Map form.
   * @param keyword word that user searches for
   * @return raw article data from the NYT API
   * @throws IOException
   */
  @Override
  public Map<String, Object> apiRequest(String keyword) throws IOException {
    String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=" + keyword + "&api-key=" + Keys.NYTKey;
    return RequestUtil.request(url);
  }
}
