package edu.brown.cs32.src.responses;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import okio.Buffer;

/** Utility for requesting URL. */
public class RequestUtil {
  /**
   * Requests a URL and deserializes it into a given class.
   *
   * @param url URL to request.
   * @return Instance of deserialized class.
   * @throws IOException If deserialization fails.
   */
  public static Map<String,Object> request(String url) throws IOException {
    URL requestConnection = new URL(url);
    HttpURLConnection clientConnection = (HttpURLConnection) requestConnection.openConnection();
    clientConnection.connect();
    Map<String,Object> response = null;

    if (clientConnection.getResponseCode() == 200) {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter serializer = moshi.adapter(
          Types.newParameterizedType(Map.class, String.class, Object.class));
      response = (Map<String,Object>) serializer.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    }

    clientConnection.disconnect();
    return response;
  }
}