package edu.brown.cs32.src.news;

import com.squareup.moshi.Types;
import edu.brown.cs32.src.interfaces.ArticleSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.brown.cs32.src.responses.RequestUtil;
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

    return articles;
  }

  public List<String> filterAbstracts(List<Map<String, Object>> articles){
    List<String> abstracts = new ArrayList<String>();
    for(int i = 0; i < articles.size(); i++){
      abstracts.add((String) articles.get(i).get("abstract"));
    }
    return abstracts;
  }
}
