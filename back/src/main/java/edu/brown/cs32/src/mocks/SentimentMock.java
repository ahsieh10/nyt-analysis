package edu.brown.cs32.src.mocks;

import edu.brown.cs32.src.interfaces.SentimentQuery;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SentimentMock implements SentimentQuery{
  @Override
  public String getSentiment(List<String> articles) throws IOException {
    return null;
  }
}
