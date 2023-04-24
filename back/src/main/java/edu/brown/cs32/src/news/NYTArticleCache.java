package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.ArticleSource;
import java.util.List;

/**
 * This class will have the cache that maps search queries to their articles and sentiments
 */
public class NYTArticleCache implements ArticleSource {
  @Override
  public List<String> getArticles(String keyword) {
    return null;
  }
}
