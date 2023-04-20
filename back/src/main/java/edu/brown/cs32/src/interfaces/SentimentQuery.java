package edu.brown.cs32.src.interfaces;

import java.util.List;
import java.util.Map;

public interface SentimentQuery{
  Map<String, Object> getSentiment(List<String> articles);
}
