package edu.brown.cs32.src.sentiment;

import edu.brown.cs32.src.privatekey.MCKey;
import edu.brown.cs32.src.responses.JSONConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * This class uses the 10 NYT article abstracts to get the sentiment value
 */
public class MCSentimentAPI {

  /**
   * Given a list of 10 article abstracts, it will combine it into one string and analyze the
   * sentiment via a call to MeaningCloud's API
   * @return the sentiment from all articles
   */
  @Override
  public String getSentiment(List<String> articles) throws IOException {
    String url = this.getURL(articles);
    String json = this.makeRequest(url);
    SentimentJson sentiment = JSONConverter.fromJson(json, SentimentJson.class);
    return sentiment.score_tag;
  }

  /**
   * Combines NYT article abstracts into one long text to be used in url for API call
   * @param articles
   * @return
   */
  private String getURL(List<String> articles) {
    // combine all article abstracts into one
    String text = "";
    for (String article : articles) {
      text += article;
    }
    text = text.replaceAll("/", "%20");
    text = text.replaceAll("=", "%20");
    text = text.replaceAll("\\?", "%20");
    String url = "https://api.meaningcloud.com/sentiment-2.1?key=" + Keys.MCKey +
    "&lang=en&txt=" + text + "&model=general";
    return url.replaceAll("\\s+","%20");
  }

  /**
   * Makes request using url and returns json
   * @param url for api request
   * @return json from api request
   */
  private String makeRequest(String url) throws IOException {
    //TODO: error handle IOException
    URL con = new URL(url);
    HttpURLConnection clientConnection = (HttpURLConnection) con.openConnection();
    clientConnection.connect();

    InputStream stream = clientConnection.getInputStream();
    BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
    StringBuilder str = new StringBuilder();

    String line;
    while ((line = buff.readLine()) != null) {
      str.append(line);
      str.append("\n");
    }
    buff.close();
    clientConnection.disconnect();
    return str.toString();
  }

}
