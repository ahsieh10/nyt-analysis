package edu.brown.cs32.src.sentiment.jsonclasses;

import java.util.List;

public class Sentence {
  public Integer confidence;
  public String score_tag;
  public List<Segment> segment_list;
  public String text;
}
