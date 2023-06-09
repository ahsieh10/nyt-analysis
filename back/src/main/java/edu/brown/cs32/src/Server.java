package edu.brown.cs32.src;

import static spark.Spark.after;


import edu.brown.cs32.src.news.NYTArticleAPI;
import edu.brown.cs32.src.news.NYTRequest;
import edu.brown.cs32.src.responses.ManualResponse;
import edu.brown.cs32.src.responses.ResponseCache;
import edu.brown.cs32.src.sentiment.MCRequest;
import edu.brown.cs32.src.sentiment.MCSentimentAPI;
import spark.Spark;

/**
 * Top-level class for our Server. Contains the main() method which starts Spark and runs the various handlers.
 */
public class Server {
  public static void main(String[] args) {

    Spark.port(4000);
        /*
            Setting CORS headers to allow cross-origin requests from the client; this is necessary for the client to
            be able to make requests to the server.

            By setting the Access-Control-Allow-Origin header to "*", we allow requests from any origin.
            This is not a good idea in real-world applications, since it opens up your server to cross-origin requests
            from any website. Instead, you should set this header to the origin of your client, or a list of origins
            that you trust.

            By setting the Access-Control-Allow-Methods header to "*", we allow requests with any HTTP method.
            Again, it's generally better to be more specific here and only allow the methods you need, but for
            this demo we'll allow all methods.

            We recommend you learn more about CORS with these resources:
                - https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
                - https://portswigger.net/web-security/cors
         */
    after((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "*");
    });

    NYTArticleAPI nyt = new NYTArticleAPI(new NYTRequest());
    MCSentimentAPI sentiment = new MCSentimentAPI(new MCRequest());

    ResponseCache query = new ResponseCache(1000, 10, new ManualResponse(nyt, sentiment));

    // Setting up the handler for the GET endpoints
    Spark.get("search", new SearchHandler(query));

    Spark.init();
    Spark.awaitInitialization();
    System.out.println("Server started.");
  }
}