package edu.brown.cs32.src.mocks;

import edu.brown.cs32.src.interfaces.ArticleSource;
import java.util.List;

public class NYTMock implements ArticleSource{
  @Override
  public List<String> getArticles(String keyword) {
    return null;
  }
}
