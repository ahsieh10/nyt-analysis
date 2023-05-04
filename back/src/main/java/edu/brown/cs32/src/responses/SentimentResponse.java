package edu.brown.cs32.src.responses;

import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.sentiment.Score;
import java.util.List;

public class SentimentResponse {
  private List<Article> articles;
  private String sentiment;
  private List<String> biased;

  /**
   * Constructor for sentiment response
   * @param articles articles fed into sentiment API
   * @param sentiment sentiment response
   * @param biased most biased sentences ranked
   */
  public SentimentResponse(List<Article> articles, String sentiment, List<String> biased){
    this.articles = articles;
    this.sentiment = sentiment;
    this.biased = biased;
  }

  /**
   * Getter for sentiment
   * @return sentiment
   */
  public String getSentiment() {
    return sentiment;
  }

  /**
   * Getter for articles
   * @return list of articles
   */
  public List<Article> getArticles() {
    return articles;
  }

  /**
   * Getter for biased
   * @return list of biased sentences
   */
  public List<String> getBiased() {
    return biased;
  }
}
