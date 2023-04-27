package edu.brown.cs32.src.sentiment;

import edu.brown.cs32.src.interfaces.SentimentQuery;
import edu.brown.cs32.src.privatekey.Keys;
import edu.brown.cs32.src.responses.JSONConverter;
import edu.brown.cs32.src.sentiment.jsonclasses.Sentence;
import edu.brown.cs32.src.sentiment.jsonclasses.Segment;
import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class uses the 10 NYT article abstracts to get the sentiment value
 */
public class MCSentimentAPI implements SentimentQuery {
  private SentimentJson sentimentData;
  private Map<String, Double> scoreMap;

  public MCSentimentAPI() {
    this.makeScoreMap();
  }

  /**
   * Given a list of 10 article abstracts, it will combine it into one string and analyze the
   * sentiment via a call to MeaningCloud's API
   * @return the sentiment from all articles
   */
  @Override
  public String getSentiment(List<String> articles) throws IOException {
    String url = this.getURL(articles);
    String json = this.makeRequest(url);
    this.sentimentData = JSONConverter.fromJson(json, SentimentJson.class);
    return this.sentimentData.score_tag;
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
//    text = text.replaceAll("/ | = | ] | \" ", " ");
//    text = text.replaceAll("'", "");
//    text = text.replaceAll("\\[", " ");
//    text = text.replaceAll("\\\\", " ");
//    text = text.replaceAll("\\^", " ");
//    text = text.replaceAll("\\*", " ");
//    text = text.replaceAll("\\?", ".");
    text = text.replaceAll("[\\p{Punct}&&[^.?!]]", " ");
    text = text.replaceAll("\\? | !", ".");
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
    //TODO: error handle IOException?
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

  // Methods below for algorithmic complexity
  /**
   * Method call to get the final list of sentences (and their scores) in order
   * @return
   * @throws IllegalStateException
   */
   public List<String> getRankedSentences() throws IllegalStateException {
//    return this.orderSentenceScores(this.assignSentenceScores());
     if (this.sentimentData == null) {
       //TODO: throw error?
       throw new IllegalStateException("Must get sentiment before ranking sentences.");
     } else {
       List<Score> scores = this.calculateScores(this.sentimentData);
       List<String> sentences = new ArrayList<>();
       for (Score score : scores) {
         sentences.add(score.getText());
       }
       return sentences;
     }
   }

//  /**
//   * Assigns sentences their scores and updates it based on confidence, polarity terms, score tags
//   * @return list of sentences and their scores, not ordered
//   * @throws IllegalStateException if this method is called before sentiment is analyzed
//   */
//  private List<Score> assignSentenceScores() throws IllegalStateException {
//    if (this.sentimentData == null) {
//      //TODO: throw error?
//      throw new IllegalStateException("Must get sentiment before ranking sentences.");
//    } else {
//      return calculateScores(this.sentimentData);
//    }
//  }

  /**
   * Calls helper methods to update scores used to rank sentences
   * (Public for testing purposes)
   * @param sentimentData - the sentiment json
   * @return returns
   */
  public List<Score> calculateScores(SentimentJson sentimentData) {
    List<Score> unorderedScores = new ArrayList<>();
    for (Sentence currSentence : sentimentData.sentence_list) {
      Score currScore = new Score(currSentence.text, this.rankScoreTags(currSentence.score_tag));
      currScore.setScore(this.rankPolarityTerms(currSentence.segment_list, currScore.getScore()));
      currScore.setScore(this.rankConfidence(currSentence.confidence, currScore.getScore()));
      unorderedScores.add(currScore);
    }
    return this.orderSentenceScores(unorderedScores);
  }

  /**
   * Uses bubble sort to sort sentences in ascending order based on score
   * @param scores the list of scores (sentences and their score values)
   * @return the ordered list of scores
   */
  private List<Score> orderSentenceScores(List<Score> scores) {
    for(int i = 0; i <scores.size() - 1; i++) {
      for(int j = 0; j <scores.size() - i - 1; j++) {

        if(scores.get(j).getScore() > scores.get(j + 1).getScore()) {
          Score temp = scores.get(j);
          scores.set(j, scores.get(j + 1));
          scores.set(j + 1, temp);
        }
      }
    }
    return scores;
  }

  /**
   * Assigns point values based on sentiment score tags of text
   */
  private void makeScoreMap() {
    this.scoreMap = new HashMap<>();
    this.scoreMap.put("P+", 20.0);
    this.scoreMap.put("P", 10.0);
    this.scoreMap.put("NEU", 0.0);
    this.scoreMap.put("NONE", 0.0);
    this.scoreMap.put("N", -10.0);
    this.scoreMap.put("N+", -20.0);
  }

  /**
   * Returns score value based on a sentence's score tag
   * @param scoreTag the score tag of the sentence to be ranked
   * @return new score
   */
  private Double rankScoreTags(String scoreTag) {
    return this.scoreMap.get(scoreTag);
  }

  /**
   * Updates score of sentences based on the number of polarity terms it contains
   * @param segments the segments within the sentence
   * @param currScore the current score of the sentence
   * @return new score
   */
  private Double rankPolarityTerms(List<Segment> segments, Double currScore) {
    int totalPolarityTerms = 0;
    for (Segment segment : segments) {
      totalPolarityTerms += segment.polarity_term_list.size();
    }
    if (totalPolarityTerms > segments.size()) {
      return currScore + (0.25 * currScore);
    } else {
      return currScore;
    }
  }

  /**
   * Updates score of sentence based on the confidence value
   * @param confidence the confidence of the sentiment score
   * @param currScore the current score of the sentence
   * @return new score
   */
  private Double rankConfidence(Integer confidence, Double currScore) {
    if (confidence < 93) {
      return currScore + (-0.4 * currScore);
    } else if (confidence < 96) {
      return currScore + (-0.3 * currScore);
    } else if (confidence < 98)  {
      return currScore + (-0.2 * currScore);
    } else {
      return currScore;
    }
  }

}
