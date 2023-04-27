package edu.brown.cs32.src;

import static edu.brown.cs32.src.news.NYTArticleAPI.filterParagraphs;
import static edu.brown.cs32.src.responses.JSONConverter.objectsMapToJson;
import static edu.brown.cs32.src.responses.ResponseCreator.handleSuccess;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.src.news.NYTArticleAPI;
import edu.brown.cs32.src.news.NYTArticleCache;
import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.responses.ResponseCreator;
import edu.brown.cs32.src.responses.SentimentResponse;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import edu.brown.cs32.src.sentiment.Score;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.Map;

/**
 * This class handles search queries and feed them into the API pipeline to return
 * a sentiment report
 */
public class SearchHandler implements Route {

  NYTArticleCache cache;

  public SearchHandler(NYTArticleCache cache){
    this.cache = cache;
  }

  @Override
  public Object handle(Request request, Response response) {

    //TODO: 1) handle search queries, 2) feed into NYT API, 3) feed into MC API 4) return sentiment
    QueryParamsMap qm = request.queryMap();
    String keyword = qm.value("keyword");

    // ERROR HANDLING
    if (qm.toMap().isEmpty() || keyword == null) {
      return ResponseCreator.handleBadRequest("keyword");
    }


    Map<String, Object> articleResponse = this.cache.getArticles(keyword);
    if(articleResponse.get("status").equals("success")){
      MCSentimentAPI sentimentLoader = new MCSentimentAPI();
      String sentiment;
      try{
        sentiment = sentimentLoader.getSentiment(filterParagraphs((List<Article>)articleResponse.get("data")));
        List<String> biased = sentimentLoader.getRankedSentences();
        return handleSuccess((List<Article>)articleResponse.get("data"), sentiment, biased);
      }
      catch(IOException e){
        Map<String, Object> finalResponse = new HashMap<String, Object>();
        finalResponse.put("status", "error_bad_request");
        finalResponse.put("error_message", "Received IOException in sentiment retrieval");
        return objectsMapToJson(finalResponse);
      }
    }
    else{
      return objectsMapToJson(articleResponse);
    }
  }

}