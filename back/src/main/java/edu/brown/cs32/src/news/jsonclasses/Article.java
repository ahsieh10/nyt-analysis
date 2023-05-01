package edu.brown.cs32.src.news.jsonclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Article {
  private String newsAbstract;
  private String webUrl;
  private String snippet;
  private String leadParagraph;
  private Integer wordCount;
  private List<String> keywords;
  private String headline;

  /**
   * Constructor for making an Article object out of a map.
   * @param articleMap map version of an article
   */
  public Article(Map<String, Object> articleMap){
    this.newsAbstract = (String) articleMap.get("abstract");
    this.webUrl = (String) articleMap.get("web_url");
    this.snippet = (String) articleMap.get("snippet");
    this.leadParagraph = (String) articleMap.get("lead_paragraph");
    this.wordCount = (Integer) articleMap.get("word_count");
    this.keywords = (List<String>) articleMap.get("keywords");
    this.headline = (String) articleMap.get("headline");
  }

  public String getLeadParagraph(){
    return this.leadParagraph;
  }
  public String getAbstract(){
    return this.newsAbstract;
  }
  public String getUrl(){
    return this.webUrl;
  }
  public String getSnippet(){ return this.snippet;}
  public Integer wordCount(){ return this.wordCount;}
  public List<String> getKeywords(){ return this.keywords;}
  public String getHeadline(){return this.headline;}
}
