package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.ArticleSource;
import edu.brown.cs32.src.news.jsonclasses.Article;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.brown.cs32.src.responses.utils.RequestUtil;
import java.util.Map;
import edu.brown.cs32.src.privatekey.Keys;

/**
 * This class uses the user search query to make a request to the NYT API and get 10 articles
 * containing the keyword
 */
public class NYTArticleAPI implements ArticleSource {

  public NYTArticleAPI(){};

  @Override
  public Map<String, Object> getArticles(String keyword) {
    String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=" + keyword + "&api-key=" + Keys.NYTKey;
    Map<String, Object> articles;
    Map<String, Object> results = new HashMap<>();
    try{
      articles = RequestUtil.request(url);
    }
    catch(IOException e){
      results.put("status", "error_bad_json");
      results.put("error_message", "IOException generated when making NYT API request.");
      return results;
    }
    if(articles.get("status").equals("OK") && ((Map<String, Object>)articles.get("response")).get("docs") != null){
      List<Article> simplified = simplifyArticle((List<Map<String, Object>>) ((Map<String, Object>)articles.get("response")).get("docs"));
      results.put("status", "success");
      results.put("data", simplified);
      return results;
    }
    else{
      results.put("status", "error_bad_request");
      results.put("error_message", "NYT API did not return a list of articles.");
      return results;
    }
  }

  public static List<String> filterParagraphs(List<Article> articles){
    List<String> paragraphs = new ArrayList<String>();
    for(int i = 0; i < articles.size(); i++){
      paragraphs.add(articles.get(i).getLeadParagraph());
    }
    return paragraphs;
  }

  public static List<String> getKeywords(List<Map<String, Object>> keywords){
    List<String> results = new ArrayList<String>();
    for(int i = 0; i < keywords.size(); i++){
      if(keywords.get(i).get("value") != null){
        results.add((String)keywords.get(i).get("value"));
      }
    }
    return results;
  }

  public static String getHeadline(Map<String, Object> headline){
    return (String)headline.get("main");
  }

  private static List<Article> simplifyArticle(List<Map<String, Object>> rawArticles){
    List<Article> finalResults = new ArrayList();
    for(int i = 0; i < rawArticles.size(); i++){
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String[] keys = new String[]{"abstract", "web_url", "snippet", "lead_paragraph", "pub_date", "word_count", "keywords", "headline"};
      for(String key : keys){
        if(key.equals("keywords")){
          resultMap.put("keywords", getKeywords((List<Map<String, Object>>)rawArticles.get(i).get("keywords")));
        }
        else if(key.equals("headline")){
          String mainHeadline = getHeadline((Map<String, Object>)rawArticles.get(i).get("headline"));
          resultMap.put("headline", mainHeadline);
        }
        else if(key.equals("word_count")){
          resultMap.put("word_count", ((Double)rawArticles.get(i).get("word_count")).intValue());
        }
        else{
          resultMap.put(key, (String)rawArticles.get(i).get(key));
        }
      }
      finalResults.add(new Article(resultMap));
    }
    return finalResults;
  }
}