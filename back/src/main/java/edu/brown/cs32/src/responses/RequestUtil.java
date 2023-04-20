package edu.brown.cs32.src.responses;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import okio.Buffer;

/** Utility for requesting URL. */
public class RequestUtil {
  /**
   * Requests a URL and deserializes it into a given class.
   *
   * @param url URL to request.
   * @param adaptTo Class to deserialize to.
   * @return Instance of deserialized class.
   * @throws IOException If deserialization fails.
   */
  public static <T> T request(String url, Class<T> adaptTo) throws IOException {
    URL requestConnection = new URL(url);
    HttpURLConnection clientConnection = (HttpURLConnection) requestConnection.openConnection();
    clientConnection.connect();
    T response = null;

    if (clientConnection.getResponseCode() == 200) {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter serializer = moshi.adapter(adaptTo);
      response = (T) serializer.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    }

    clientConnection.disconnect();
    return response;
  }
}
