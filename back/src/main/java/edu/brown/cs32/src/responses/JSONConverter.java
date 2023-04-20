package edu.brown.cs32.src.responses;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
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
  }
