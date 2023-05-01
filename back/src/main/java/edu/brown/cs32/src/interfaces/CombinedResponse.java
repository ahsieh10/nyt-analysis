package edu.brown.cs32.src.interfaces;

import java.util.Map;

public interface CombinedResponse {

  /**
   * Returns the article + sentiment response to the front end
   * @param keyword keyword that user queries for
   * @return article + sentiment responses
   */
  String getResponse(String keyword);
}
