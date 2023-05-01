package edu.brown.cs32.src.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SentimentQuery{

  /**
   * Method that returns the sentiment in a list of strings
   * @param articles lead paragraphs from list of articles
   * @return overall sentiment in those lead paragraphs
   * @throws IOException
   */
  String getSentiment(List<String> articles) throws IOException;
}
