package edu.brown.cs32.src;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.brown.cs32.mocks.NYTMock;
import edu.brown.cs32.src.news.jsonclasses.Article;
import edu.brown.cs32.src.news.NYTArticleAPI;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Arrays;
import edu.brown.cs32.mocks.MockArticles;

public class TestNYTArticleAPI {

  @Test
  public void testGetArticles() throws IOException {
    NYTArticleAPI api = new NYTArticleAPI(new NYTMock());
    Map<String, Object> results = api.getArticles("korea");
    assertEquals(results.get("status"),"success");
    assertEquals(results.keySet().size(), 2);
    List<Article> articles = (List<Article>)results.get("data");
    assertEquals(articles.size(), 2);
    assertEquals(articles.get(0).getAbstract(), "As Xi Jinping and the Communist Party further exercise control over the economy, the financial sector is coming under close scrutiny.");
    assertEquals(articles.get(0).getUrl(), "https://www.nytimes.com/2023/04/27/business/china-banker-crackdown.html");
    assertEquals(articles.get(0).getSnippet(), "As Xi Jinping and the Communist Party further exercise control over the economy, the financial sector is coming under close scrutiny.");
    assertEquals(articles.get(0).getLeadParagraph(), "For years, Xi Jinping, China’s leader, has railed against greed and corruption in the country’s financial sector, making an example of a few prominent figures along the way.");
    assertEquals(articles.get(0).wordCount(), 1149);
    assertEquals(articles.get(0).getKeywords(), Arrays.asList(new String[]{"Central Commission for Discipline Inspection (Communist Party of China)",
        "Communist Party of China",
        "Regulation and Deregulation of Industry",
        "Banking and Financial Institutions",
        "China",
        "Xi Jinping",
    }));
    assertEquals(articles.get(0).getHeadline(), "China Is Cracking Down on Bankers. Here Are Some of the Targets.");
    assertEquals(articles.get(0).getThumbnail(), "images/2023/04/25/multimedia/00China-Finance-Crackdown-01-bvhw/00China-Finance-Crackdown-01-bvhw-thumbStandard.jpg");
    assertEquals(articles.get(1).getThumbnail(), "");
  }

  @Test
  public void testFilterParagraphs(){
    NYTArticleAPI api = new NYTArticleAPI(new NYTMock());
    Map<String, Object> results = api.getArticles("korea");
    List<Article> articles = (List<Article>)results.get("data");
    List<String> paragraphs = NYTArticleAPI.filterParagraphs(articles);
    assertEquals(paragraphs.size(), 2);
    assertEquals(paragraphs, Arrays.asList(new String[]{"For years, Xi Jinping, China’s leader, has railed against greed and corruption in the country’s financial sector, making an example of a few prominent figures along the way.", "TAIPEI, Taiwan — A Taiwan-based publisher who disappeared while in China has been detained for suspected violations of security laws, Chinese authorities confirmed on Wednesday, fanning concerns in Taiwan that Beijing is sending a warning to the island’s vibrant publishing sector."}));
  }

  @Test
  public void testNonexistentKeyword(){
    NYTArticleAPI api = new NYTArticleAPI(new NYTMock());
    Map<String, Object> results = api.getArticles("hello");
    assertEquals(results.get("status"), "error_bad_json");
    assertEquals(results.get("error_message"), "Invalid input.");
  }
}