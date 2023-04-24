package edu.brown.cs32.src.sentiment;

public class Score {
  private String text;
  private Double score;

  public Score(String text, Double score) {
    this.text = text;
    this.score = score;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }
}
