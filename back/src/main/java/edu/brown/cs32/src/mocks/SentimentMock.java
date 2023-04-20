package edu.brown.cs32.src.mocks;

import edu.brown.cs32.src.interfaces.SentimentQuery;
import java.util.List;
import java.util.Map;

public class SentimentMock implements SentimentQuery{
  @Override
  public Map<String, Object> getSentiment(List<String> articles){
    return null;
  }
}
