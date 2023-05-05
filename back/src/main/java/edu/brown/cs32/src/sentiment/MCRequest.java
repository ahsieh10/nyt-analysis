package edu.brown.cs32.src.sentiment;

import edu.brown.cs32.src.interfaces.MCInter;
import edu.brown.cs32.src.privatekey.Keys;
import edu.brown.cs32.src.responses.utils.JSONConverter;
import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Class that calls MC API to get sentiment json
 */
public class MCRequest implements MCInter {
  /**
   * Given a list of 10 article abstracts, it will combine it into one string and analyze the
   * sentiment via a call to MeaningCloud's API
   * @return the sentiment from all articles
   */
  @Override
  public SentimentJson apiRequest(List<String> articles) throws IOException {
    String url = this.getURL(articles);
    String json = this.makeRequest(url);
    Map<String, Object> temp = JSONConverter.fromJson(json, Map.class);
    if(temp.get("agreement") == null){
      throw new IOException();
    }
    return JSONConverter.fromJson(json, SentimentJson.class);
  }


  /**
   * Combines NYT article abstracts into one long text to be used in url for API call
   * (public only for testing purposes)
   * @param articles - list of text from NYT articles
   * @return properly formatted url for MC API request
   */
  public String getURL(List<String> articles) {
    // combine all article abstracts into one
    String text = "";
    for (String article : articles) {
      text += article;
    }

    text = text.replaceAll("[\\p{Punct}&&[^.?!,:;]]", " ");
    text = text.replaceAll("\\?", ".");
    text = text.replaceAll("!", ".");
    text = text.replaceAll("—", " ");
    text = text.replaceAll("”", " ");
    text = text.replaceAll("“", " ");
    if (text.length() > 4000) {
      text = text.substring(0, 4000);
    }
    String url = "https://api.meaningcloud.com/sentiment-2.1?key=" + Keys.MCKey +
        "&lang=en&txt=" + text + "&model=general";
    url = url.replaceAll("\\s+","%20");
    return url;
  }

  /**
   * Makes request using url and returns json
   * @param url for api request
   * @return json from api request
   */
  private String makeRequest(String url) throws IOException {
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
