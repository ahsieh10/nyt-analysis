package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.ArticleSource;
import java.util.List;

/**
 * This class uses the user search query to make a request to the NYT API and get 10 articles
 * containing the keyword
 */
public class NYTArticleAPI implements ArticleSource {

  @Override
  public List<String> getArticles(String keyword) {
    return null;
  }
}
