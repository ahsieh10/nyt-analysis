package edu.brown.cs32.src.mocks;

import edu.brown.cs32.src.interfaces.ArticleSource;
import java.util.List;
import java.util.Map;

public class NYTMock implements ArticleSource{
  @Override
  public Map<String, Object>  getArticles(String keyword) {
    return null;
  }
}