package edu.brown.cs32.src.sentiment;

/**
 * Class that wraps sentence's text and score
 */
public class Score {
  private String text;
  private Double score;

  /**
   * Constructor to initialize sentence text and initial score
   * @param text the text of the sentence
   * @param score the score of the sentence
   */
  public Score(String text, Double score) {
    this.text = text;
    this.score = score;
  }

  /**
   * Getter for sentence text
   * @return sentence text
   */
  public String getText() {
    return text;
  }


  /**
   * Getter for sentence score
   * @return sentence score
   */
  public Double getScore() {
    return score;
  }

  /**
   * Setter for sentence score
   * @param score score to be updated
   */
  public void setScore(Double score) {
    this.score = score;
  }
}
