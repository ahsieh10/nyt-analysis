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

  public Article(Map<String, Object> articleMap){
    this.newsAbstract = (String) articleMap.get("abstract");
    this.webUrl = (String) articleMap.get("web_url");
    this.snippet = (String) articleMap.get("snippet");
    this.leadParagraph = (String) articleMap.get("lead_paragraph");
    this.wordCount = (Integer) articleMap.get("word_count");
    this.headline = (String) articleMap.get("headline");
  }

  public String getLeadParagraph(){
    return this.leadParagraph;
  }
}
