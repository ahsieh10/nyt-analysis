package edu.brown.cs32.src.news;

import edu.brown.cs32.src.interfaces.ArticleSource;
import java.util.Map;
import com.google.common.cache.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * This class will have the cache that maps search queries to their articles and sentiments
 */
public class NYTArticleCache implements ArticleSource {
  /**
   * Storing cache and distance as private fields (distance = how close two coordinates can be to
   * return the same value)
   */
  private LoadingCache<String, Map<String, Object>> cache;

  private double distance;

  private NYTArticleAPI naive;

  /**
   * Cache constructor. Sets cache eviction rules to time and size as given by the developer, and
   * the developer can also choose the distance as which to consider two points the same.
   *
   * @param time Number of seconds before cache entry is considered to be expired.
   * @param size Maximum number of entries in cache before entries are evicted.
   * @param request Manual version of API request (what to call when entry is not in cache)
   */
  public NYTArticleCache(int time, int size, NYTArticleAPI request) {
    this.naive = request;
    this.cache =
        CacheBuilder.newBuilder()
            .maximumSize(size)
            .expireAfterAccess(time, TimeUnit.SECONDS)
            .recordStats()
            .build(
                new CacheLoader<String, Map<String, Object>>() {
                  // If user entry does not exist in the cache, call startRequest function of manual
                  // handler.
                  @Override
                  public Map<String, Object> load(String key) throws Exception {
                    return naive.getArticles(key);
                  }
                });
  }


  /**
   * Handles incoming request by checking whether the results have been stored in the cache. If so,
   * return from the cache. If not, manually fetch the value from the API and store it in the cache.
   *
   * @param keyword Key containing coordinates to query for in the API
   * @return Returns results of Weather API call (should have a length of 2 with "temperature" and
   *     "unit" as keys
   * @throws IOException thrown by ManualRequest (handled by Guava cache built ins)
   * @throws ArrayIndexOutOfBoundsException thrown by ManualRequest (handled by Guava cache built
   *     ins)
   */
  @Override
  public Map<String, Object> getArticles(String keyword){
    ConcurrentMap<String, Map<String, Object>> mappedCache = this.cache.asMap();
    // Iterate through each key to see if the key is close enough (by distance) to any key in the
    // map
    for (String otherKey : mappedCache.keySet()) {
      if (keyword.equals(otherKey)) {
        // Returns results stored in cache if key is close enough
        Map<String, Object> results = this.cache.getUnchecked(otherKey);
        System.out.println(this.cache.stats());
        return results;
      }
    }
    // Manually retrieves results of request
    Map<String, Object> results = this.cache.getUnchecked(keyword);
    System.out.println(this.cache.stats());
    return results;
  }

  /**
   * Returns contents of the cache in the form of a concurrent map (Testing only)
   *
   * @return Cache in the form of a concurrent map
   */
  public ConcurrentMap<String, Map<String, Object>> getCache() {
    return this.cache.asMap();
  }

  /**
   * Returns cache statistics (Testing only)
   *
   * @return Statistics of the cache
   */
  public CacheStats getStats() {
    return this.cache.stats();
  }
}