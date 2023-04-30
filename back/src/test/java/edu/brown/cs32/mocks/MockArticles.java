package edu.brown.cs32.mocks;

import edu.brown.cs32.src.news.jsonclasses.Article;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class MockArticles {

  public static final Map<String, Object> basicArticleMap = new HashMap<String, Object>(){{
    put("abstract", "The Treasury secretary laid out a vision in which the United States could continue cooperating economically with China despite what she described as acute security threats.\",\"web_url\":\"https://www.nytimes.com/2023/04/20/business/economy/yellen-us-china-relationship.html\",\"snippet\":\"The Treasury secretary laid out a vision in which the United States could continue cooperating economically with China despite what she described as acute security threats.");
    put("web_url", "https://www.nytimes.com/2023/04/20/business/economy/yellen-us-china-relationship.html");
    put("snippet", "The Treasury secretary laid out a vision in which the United States could continue cooperating economically with China despite what she described as acute security threats.");
    put("lead_paragraph", "WASHINGTON — Treasury Secretary Janet L. Yellen on Thursday called for a “constructive” and “healthy” economic relationship between the United States and China, one in which the two nations could work together to confront global challenges in spite of their conflicting national security interests.");
    put("word_count", 200);
    put("keywords", Arrays.asList(new String[]{"hello", "my"}));
    put("headline", "Yellen Calls for ‘Constructive’ China Relationship");
  }};

  public static final Article basicArticle = new Article(basicArticleMap);

}
