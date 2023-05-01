package edu.brown.cs32.src.responses;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import edu.brown.cs32.src.interfaces.CombinedResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ResponseCache implements CombinedResponse {
  /**
   * Cache that stores keywords to their json responses of articles + sentiment
   */
  private LoadingCache<String, String> cache;
  private ManualResponse naive;

  /**
   * Cache constructor. Sets cache eviction rules to time and size as given by the developer, and
   * the developer can also choose the distance as which to consider two points the same.
   *
   * @param time Number of seconds before cache entry is considered to be expired.
   * @param size Maximum number of entries in cache before entries are evicted.
   * @param request Manual version of API request (what to call when entry is not in cache)
   */
  public ResponseCache(int time, int size, ManualResponse request) {
    this.naive = request;
    this.cache =
        CacheBuilder.newBuilder()
            .maximumSize(size)
            .expireAfterAccess(time, TimeUnit.SECONDS)
            .recordStats()
            .build(
                new CacheLoader<String, String>() {
                  // If user entry does not exist in the cache, call startRequest function of manual
                  // handler.
                  @Override
                  public String load(String key) throws Exception {
                    return naive.getResponse(key);
                  }
                });
  }


  /**
   * Handles incoming request by checking whether the results have been stored in the cache. If so,
   * return from the cache. If not, manually fetch the value from the API and store it in the cache.
   *
   * @param keyword Key containing coordinates to query for in the API
   * @return Returns results of article query + sentiment query as a json string
   */
  @Override
  public String getResponse(String keyword){
    ConcurrentMap<String, String> mappedCache = this.cache.asMap();
    // Iterate through each key to see if the key is close enough (by distance) to any key in the
    // map
    for (String otherKey : mappedCache.keySet()) {
      if (keyword.equals(otherKey)) {
        // Returns results stored in cache if key is close enough
        String results = this.cache.getUnchecked(otherKey);
        System.out.println(this.cache.stats());
        return results;
      }
    }
    // Manually retrieves results of request
    String results = this.cache.getUnchecked(keyword);
    System.out.println(this.cache.stats());
    return results;
  }

  /**
   * Returns contents of the cache in the form of a concurrent map (Testing only)
   *
   * @return Cache in the form of a concurrent map
   */
  public ConcurrentMap<String, String> getCache() {
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
