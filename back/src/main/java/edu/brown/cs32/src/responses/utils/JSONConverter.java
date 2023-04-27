package edu.brown.cs32.src.responses.utils;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

  /**
   * The JSONConverter converts a Map to a JSON object.
   */
  public class JSONConverter {

    /**
     * This method allows the conversion of a Map<String, Object> to a moshi adapter JSON.
     * @param json
     * @return
     */
    public static String objectsMapToJson(Map<String, Object> json){
      Moshi moshi = new Moshi.Builder().build();
      Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
      return moshi.adapter(type).toJson(json);
    }

    /**
     * Uses Json to make the object that is passed in
     * @param obj - the class that the json will be converted to
     * @param <T> - the type of the class
     * @return - the object with t
     * @throws IOException if buffer encounters issue
     */
    public static <T> T fromJson(String json, Class<T> obj) throws IOException {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<T> adapter = moshi.adapter(obj);
      return adapter.fromJson(json);
    }

    /**
     * Converts object to json
     * @param obj - the class that the json will be converted to
     * @param <T> - the type of the class
     * @return - the object with t
     * @throws IOException if buffer encounters issue
     */
    public static <T> String toJson(T obj, Class<T> type) throws IOException {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<T> adapter = moshi.adapter(type);
      return adapter.toJson(obj);
    }
  }
