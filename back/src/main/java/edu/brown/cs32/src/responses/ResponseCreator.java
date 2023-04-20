package edu.brown.cs32.src.responses;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class will make the maps for success and failure responses
 */
public class ResponseCreator {
  /**
   * Returns json in case of missing parameter requests.
   * @return json
   */
  public static String handleBadRequest(String expectedParams){
    Map<String, Object> resultMap = new LinkedHashMap<>();
    resultMap.put("result", "error_bad_request");
    resultMap.put("error_message", "missing parameters. Make sure to include: " + expectedParams);
    return JSONConverter.objectsMapToJson(resultMap);
  }

  //TODO: make maps for success / error responses (left some old code below for reference)
//  /**
//   * Returns json in case of filtering/searching an empty intial geoJson data.
//   * @return Object json
//   */
//  public static String handleEmptyData(){
//    Map<String, Object> resultMap = new LinkedHashMap<>();
//    resultMap.put("result", "error_datasource");
//    resultMap.put("error_message", "could not get geojson data source");
//    return JSONConverter.objectsMapToJson(resultMap);
//  }
//
//
//  /**
//   * Returns the initial geoJson if no filter params are provided.
//   * @return json
//   */
//  public static String handleNoFilterParams(GeoJsonInitializer geoJson){
//    Map<String, Object> resultMap = new LinkedHashMap<>();
//    resultMap.put("result", "success");
//    resultMap.put("data", geoJson.getGeoJson());
//    return JSONConverter.objectsMapToJson(resultMap);
//  }
//
//  /**
//   * Returns an empty geojson if no search params are provided.
//   * @return Object json
//   */
//  public static String handleNoSearchParams(){
//    Map<String, Object> resultMap = new LinkedHashMap<>();
//    resultMap.put("result", "success");
//    GeoJson initialGeoJson = new GeoJson();
//    initialGeoJson.features = new ArrayList<>();
//    initialGeoJson.type = "FeatureCollection";
//    resultMap.put("data", initialGeoJson);
//    return JSONConverter.objectsMapToJson(resultMap);
//  }
//
//  /**
//   * Returns error message if filter params are not doubles.
//   * @return
//   */
//  public static String handleNumberFormatException(){
//    Map<String, Object> resultMap = new LinkedHashMap<>();
//    resultMap.put("result", "error_bad_json");
//    resultMap.put("error_message", "all parameters must be doubles");
//    return JSONConverter.objectsMapToJson(resultMap);
//  }

}
