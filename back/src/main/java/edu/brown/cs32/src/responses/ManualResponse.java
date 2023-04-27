package edu.brown.cs32.src.responses;

import static edu.brown.cs32.src.news.NYTArticleAPI.filterParagraphs;
import static edu.brown.cs32.src.responses.ResponseCreator.handleSuccess;
import static edu.brown.cs32.src.responses.utils.JSONConverter.objectsMapToJson;

import edu.brown.cs32.src.interfaces.CombinedResponse;
import edu.brown.cs32.src.news.NYTArticleAPI;
import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManualResponse implements CombinedResponse {

  private NYTArticleAPI nytQuery;
  private MCSentimentAPI sentimentLoader;

  public ManualResponse(NYTArticleAPI nytQuery, MCSentimentAPI sentimentLoader){
    this.nytQuery = nytQuery;
    this.sentimentLoader = sentimentLoader;
  }
  @Override
  public String getResponse(String keyword){

    Map<String, Object> articleResponse = this.nytQuery.getArticles(keyword);
    if(articleResponse.get("status").equals("success")){
      String sentiment;
      try{
        sentiment = this.sentimentLoader.getSentiment(filterParagraphs((List<Article>)articleResponse.get("data")));
        List<String> biased = this.sentimentLoader.getRankedSentences();
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
