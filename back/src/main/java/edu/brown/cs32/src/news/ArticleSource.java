package edu.brown.cs32.src.news;

import java.util.List;

/**
 * This is interface that both the NYTArticleAPI and the NYTArticleCache implement. Good for
 * strategy pattern, may also be helpful for testing/mocking later
 */
public interface ArticleSource {
  List<String> getArticles();
}
