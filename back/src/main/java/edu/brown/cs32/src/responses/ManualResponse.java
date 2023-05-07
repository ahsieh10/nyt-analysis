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

/**
  * Response class that gets data from both NYT and MC API calls and formats
  * into a response
  */
public class ManualResponse implements CombinedResponse {

  private NYTArticleAPI nytQuery;
  private MCSentimentAPI sentimentLoader;

  /**
   * Constructor
   * @param nytQuery either real or mocked NYT API call
   * @param sentimentLoader either real or mocked sentiment API call
   */
  public ManualResponse(NYTArticleAPI nytQuery, MCSentimentAPI sentimentLoader){
    this.nytQuery = nytQuery;
    this.sentimentLoader = sentimentLoader;
  }

  /**
   * Makes a call to the NYT API and retrieves the articles, filters out the lead paragraphs and feeds them
   * into the MCSentimentAPI. The overall sentiment and most biased sentences are retrieved, and the results are serialized into
   * a json string and returned.
   * @param keyword keyword fed by user
   * @return list of articles + sentiment + most biased sentences
   */
  @Override
  public String getResponse(String keyword){
    Map<String, Object> articleResponse = this.nytQuery.getArticles(keyword);
    if(articleResponse.get("status").equals("success")){
      String sentiment;
      try{
        List<String> text = filterParagraphs((List<Article>)articleResponse.get("data"));
        sentiment = this.sentimentLoader.getSentiment(text);
        List<String> biased = this.sentimentLoader.getRankedSentences();
        return handleSuccess((List<Article>)articleResponse.get("data"), sentiment, biased);
      }
      catch(IOException e){
        Map<String, Object> finalResponse = new HashMap<String, Object>();
        finalResponse.put("status", "error_bad_request");
        finalResponse.put("error_message", "Received IOException in sentiment retrieval. Check your inputted keyword.");
        return objectsMapToJson(finalResponse);
      }
    }
    else{
      return objectsMapToJson(articleResponse);
    }
  }
}
