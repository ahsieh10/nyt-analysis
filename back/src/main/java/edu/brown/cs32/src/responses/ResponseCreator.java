package edu.brown.cs32.src.responses;

import com.squareup.moshi.Moshi;
import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.responses.utils.JSONConverter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.squareup.moshi.JsonAdapter;

/**
 * This class will make the maps for success and failure responses
 */
public class ResponseCreator {
  /**
   * Returns json in case of missing parameter requests.
   * @return json
   */
  public static String handleBadRequest(String expectedParams){
    Map<String, Object> resultMap = new LinkedHashMap<>();
    resultMap.put("result", "error_bad_request");
    resultMap.put("error_message", "missing parameters. Make sure to include: " + expectedParams);
    return JSONConverter.objectsMapToJson(resultMap);
  }

  /**
   * Handles logic for returning a success response.
   * @param articles list of articles retrieved from the NYT API
   * @param sentiment overall sentiment detected from the articles
   * @param biased list of most biased sentences in the lead paragraphs
   * @return json string of combined results
   */
  public static String handleSuccess(List<Article> articles, String sentiment, List<String> biased){
    Map<String, Object> results = new HashMap<String, Object>();
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter serializer = moshi.adapter(Map.class);
    results.put("result", "success");
    results.put("data", new SentimentResponse(articles, sentiment, biased));
    return serializer.toJson(results);
  }

}
