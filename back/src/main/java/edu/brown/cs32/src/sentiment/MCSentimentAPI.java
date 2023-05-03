package edu.brown.cs32.src.sentiment;

import edu.brown.cs32.src.interfaces.MCInter;
import edu.brown.cs32.src.sentiment.jsonclasses.Sentence;
import edu.brown.cs32.src.sentiment.jsonclasses.Segment;
import edu.brown.cs32.src.sentiment.jsonclasses.SentimentJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class uses the 10 NYT article abstracts to get the sentiment value
 */
public class MCSentimentAPI {
  private SentimentJson sentimentData;
  private Map<String, Double> scoreMap;
  private MCInter requester;

  /**
   * Constructor for MCSentimentAPI. Takes in a requester that should be able to make a request for
   * a sentiment json. Assigns initial score map based on score tags.
   * @param requester
   */
  public MCSentimentAPI(MCInter requester) {
    this.requester = requester;
    this.makeScoreMap();
  }

  /**
   * Given a list of articles, return the overall sentiment.
   * @param articles - the list of articles from NYT
   * @return - score tag (P, NEU, N, etc.)
   * @throws IOException
   */
  public String getSentiment(List<String> articles) throws IOException {
    this.sentimentData = this.requester.apiRequest(articles);
    return this.sentimentData.score_tag;
  }

  // Methods below for algorithmic complexity
  /**
   * Method call to get the final list of sentences (and their scores) in order
   * @return - the list of sentences in order from least to most biased
   * @throws IllegalStateException
   */
   public List<String> getRankedSentences() throws IllegalStateException {
     if (this.sentimentData == null) {
       throw new IllegalStateException("Must get sentiment before ranking sentences.");
     } else {
       List<Score> scores = this.calculateScores(this.sentimentData);
       return this.filterSentences(scores);
     }
   }

  /**
   * Filters list of sentences to get rid of incomplete sentences and sentences that don't agree
   * with overall sentiment.
   * (Public for testing purposes)
   * @param scores - the current list of scores
   * @return - the filtered list of sentences
   */
   public List<String> filterSentences(List<Score> scores) {
     List<String> filtered = new ArrayList<>();
     for (Score score : scores) {
       String sentence = score.getText();
       // exclude incomplete sentences and sentences with opposite sentiment of overall sentiment
       if (!sentence.endsWith(".") || !Character.isUpperCase(sentence.charAt(0))
           || Math.signum(score.getScore()) == -1 * Math.signum(this.scoreMap.get(this.sentimentData.score_tag))) {
         continue;
       } else {
         filtered.add(sentence);
       }
     }
     return filtered;
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

        if(this.compare(scores, j)) {
          Score temp = scores.get(j);
          scores.set(j, scores.get(j + 1));
          scores.set(j + 1, temp);
        }
      }
    }
    return scores;
  }

  /**
   * Helper method that switches sorting order based on the overall sentiment
   * @param scores - list of scores
   * @param j - the current iteration
   * @return true based on comparison result
   */
  private boolean compare(List<Score> scores, int j) {
    if (Math.signum(this.scoreMap.get(this.sentimentData.score_tag)) == -1) {
      return scores.get(j).getScore() < scores.get(j + 1).getScore();
    } else {
      return scores.get(j).getScore() > scores.get(j + 1).getScore();
    }
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
