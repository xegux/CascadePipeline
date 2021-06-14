package pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** Takes a JSON file and converts it to an instance of Java LinkedHashMap. */
public class Read {
    private URL url_string;
    private String jsonString;
    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private ArrayList<Map<String, Object>> jsonList;
    private LinkedHashMap<String, Object> jsonMap;

    /** An empty constructor. */
    private Read() {
    }

    /** Constructor: a Read object with an input JSON file converted to a Java String. */
    public Read(String url_string) {
        try {
            this.url_string = new URL(url_string);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.url_string.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
                stringBuilder.append(System.lineSeparator());
            }
            bufferedReader.close();
            jsonString = stringBuilder.toString().trim();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** Returns a String form of the JSON file. */
    public String getJsonString() {
        return jsonString;
    }

    /** Returns an ArrayList form of the JSON array. */
    public ArrayList<Map<String, Object>> getJsonList() {
        return jsonList;
    }

    /** Returns a LinkedHashMap form of the JSON object. */
    public LinkedHashMap<String, Object> getJsonMap() {
        return jsonMap;
    }

    /** Converts the String form of the JSON file into a Java data structure. */
    public void toJsonElement() {
        if (jsonString.charAt(0) == '[') { // JSON array
            toArrayList();
        } else { // JSON object
            toLinkedHashMap();
        }
    }

    /** Returns the size of the input JSON array. */
    public int arraySize() {
        if (jsonArray == null) return 0;
        return jsonArray.size();
    }

    /** Converts jsonArray into Java ArrayList. */
    private void toArrayList() {
        jsonList = new ArrayList<>();
        toJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            jsonMap = new Gson().fromJson(object, LinkedHashMap.class);
            jsonList.add(jsonMap);
        }
    }

    /**Converts jsonObject into Java LinkedHashMap. */
    private void toLinkedHashMap() {
        toJsonObject();
        jsonMap = new Gson().fromJson(jsonObject, LinkedHashMap.class);
    }

    /** Converts the String form of the JSON file into a JsonArray when jsonString starts and ends with square brackets. */
    private void toJsonArray() {
        jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
    }

    /** Converts the String form of the JSON file into a JsonObject when jsonString starts and ends with curly brackets. */
    private void toJsonObject() {
        jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
    }
}
