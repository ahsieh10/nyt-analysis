package edu.brown.cs32.src.interfaces;

import java.util.List;
import java.util.Map;

public interface SentimentQuery{
  String getSentiment(List<String> articles);
}
