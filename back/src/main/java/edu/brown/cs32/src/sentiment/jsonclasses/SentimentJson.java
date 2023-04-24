package edu.brown.cs32.src.sentiment.jsonclasses;

import java.util.List;

public class SentimentJson {
  public String model;
  public String score_tag;
  public String agreement;
  public String subjectivity;
  public Integer confidence;
  public String irony;
  public List<Sentence> sentence_list;

//  public record Sentiment(
//      @Json(name = "status") Status status,
//      @Json(name = "model") String model,
//      @Json(name = "score_tag") String scoreTag,
//      @Json(name = "agreement") String agreement,
//      @Json(name = "subjectivity") String subjectivity,
//      @Json(name = "confidence") int confidence,
//      @Json(name = "irony") String irony
//  ){}
//
//  public record Status (
//      @Json(name = "code") int code,
//      @Json(name = "msg") String message,
//      @Json(name = "credits") int credits
//  ){}
}
