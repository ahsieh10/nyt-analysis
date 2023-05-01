package edu.brown.cs32.src.interfaces;

import java.io.IOException;
import java.util.Map;

public interface NYTInter {

  /**
   * Returns the results of a NYT API request (either real or mocked)
   * @param keyword keyword that user queries for on the front end
   * @return returns NYT articles based on that keyword
   * @throws IOException
   */
  Map<String, Object> apiRequest(String keyword) throws IOException;
}
