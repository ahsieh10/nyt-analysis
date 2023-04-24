package edu.brown.cs32.src;

import edu.brown.cs32.src.responses.ResponseCreator;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This class handles search queries and feed them into the API pipeline to return
 * a sentiment report
 */
public class SearchHandler implements Route {

  public SearchHandler(){}

  @Override
  public Object handle(Request request, Response response) {
    //TODO: 1) handle search queries, 2) feed into NYT API, 3) feed into MC API 4) return sentiment
    QueryParamsMap qm = request.queryMap();
    String keyword = qm.value("keyword");

    // ERROR HANDLING
    if (qm.toMap().isEmpty() || keyword == null) {
      return ResponseCreator.handleBadRequest("keyword");
    }

    return "";

  }
}
