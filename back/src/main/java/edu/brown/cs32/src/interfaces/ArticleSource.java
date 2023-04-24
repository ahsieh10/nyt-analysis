package edu.brown.cs32.src.interfaces;

import java.util.List;
import java.util.Map;

/**
 * This is interface that both the NYTArticleAPI and the NYTArticleCache implement. Good for
 * strategy pattern, may also be helpful for testing/mocking later
 */
public interface ArticleSource {
  Map<String, Object> getArticles(String keyword);
}