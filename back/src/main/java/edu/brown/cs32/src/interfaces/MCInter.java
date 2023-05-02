package edu.brown.cs32.src.interfaces;

import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MCInter {

  /**
   * Method that returns the sentiment in a list of strings
   * @param articles lead paragraphs from list of articles
   * @return overall sentiment in those lead paragraphs
   * @throws IOException
   */
//  String getSentiment(List<String> articles) throws IOException;
  SentimentJson apiRequest(List<String> articles) throws IOException;
}
