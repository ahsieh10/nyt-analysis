package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.NYTInter;
import edu.brown.cs32.src.news.jsonclasses.Article;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.brown.cs32.src.responses.utils.RequestUtil;
import java.util.Map;
import edu.brown.cs32.src.privatekey.Keys;
import edu.brown.cs32.src.news.NYTRequest;

/**
 * This class uses the user search query to make a request to the NYT API and get 10 articles
 * containing the keyword
 */
public class NYTArticleAPI{
  private final NYTInter requester;

  /**
   * Constructor
   * @param request an object that either makes a real or mocked NYT API request.
   */
  public NYTArticleAPI(NYTInter request){
    this.requester = request;
  };

  /**
   * Makes an API request to the NYT API and converts the results to a list of Article objects
   * @param keyword keyword to search by in the NYT aPI
   * @return returns a map with a status key (success, error) and either the list of articles or an error message.
   */
  public Map<String, Object> getArticles(String keyword) {
    Map<String, Object> articles;
    Map<String, Object> results = new HashMap<>();
    try{
      articles = this.requester.apiRequest(keyword);
    }
    catch(IOException e){
      results.put("status", "error_bad_json");
      results.put("error_message", "IOException generated when making NYT API request.");
      return results;
    }
    if(articles == null){
      results.put("status", "error_bad_json");
      results.put("error_message", "Invalid input.");
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

  /**
   * Takes a list of articles and returns a list of their lead paragraphs (for sentiment extracting)
   * @param articles list of articles
   * @return list of lead paragraphs
   */
  public static List<String> filterParagraphs(List<Article> articles){
    List<String> paragraphs = new ArrayList<String>();
    for(int i = 0; i < articles.size(); i++){
      paragraphs.add(articles.get(i).getLeadParagraph());
    }
    return paragraphs;
  }

  private static String filterThumbnail(List<Map<String, Object>> multimedia){
    int index = 0;
    while(index < multimedia.size() && !multimedia.get(index).get("subtype").equals("thumbnail")){
      index++;
    }
    if(index >= multimedia.size()){
      return "";
    }
    return (String) multimedia.get(index).get("url");
  }

  /**
   * Gets the keywords associated with an article (still in map form). Used to create the Article object.
   * @param keywords map object that contains the keywords along with many other parameters
   * @return just the values of the keyword maps
   */
  private static List<String> getKeywords(List<Map<String, Object>> keywords){
    List<String> results = new ArrayList<String>();
    for(int i = 0; i < keywords.size(); i++){
      if(keywords.get(i).get("value") != null){
        results.add((String)keywords.get(i).get("value"));
      }
    }
    return results;
  }

  /**
   * Retrieves the main headline of the headline Map for an article.
   * @param headline headline map associated with an article
   * @return main headline of the article
   */
  private static String getHeadline(Map<String, Object> headline){
    return (String)headline.get("main");
  }

  /**
   * Returns the articles returned from the API as article objects, removing unnecessary information.
   * @param rawArticles Article data directly from the NYT API
   * @return list of articles with simplified parameters
   */
  private static List<Article> simplifyArticle(List<Map<String, Object>> rawArticles){
    List<Article> finalResults = new ArrayList();
    for(int i = 0; i < rawArticles.size(); i++){
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String[] keys = new String[]{"abstract", "web_url", "snippet", "lead_paragraph", "pub_date", "word_count", "keywords", "headline", "multimedia"};
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
        else if(key.equals("multimedia")){
          if(rawArticles.get(i).get("multimedia") == null){
            resultMap.put("thumbnail", "");
          }
          else{
            resultMap.put("thumbnail", filterThumbnail((List<Map<String, Object>>) rawArticles.get(i).get("multimedia")));
          }
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