package COURSE02148.homeautomation.server.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WitResponse {

    private HashMap<String, Object> jsonMap;

    public WitResponse(String witResult) {
        try {
            createJsonMap(witResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Object> createJsonMap(String json) throws JSONException {
        jsonMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(json);

        String prefix = "";
        iterateJson(jsonObject, jsonMap, prefix);

        return jsonMap;

    }

    private void iterateJson(JSONObject json, HashMap<String, Object> jsonMap, String prefix) throws JSONException {

        if (json == JSONObject.NULL) return;

        Iterator keys = json.keys();

        while(keys.hasNext()){
            String key = (String) keys.next();
            Object value = json.get(key);

            String mappedKey;
            if (prefix.isEmpty()){
                mappedKey = key;
            } else {
                mappedKey = prefix + "." + key;
            }

            if (value instanceof JSONArray){
                JSONArray array = (JSONArray) value;
                if (array.length() == 0) continue;
                iterateJson(array.getJSONObject(0), jsonMap, mappedKey);
            } else if (value instanceof  JSONObject) {
                JSONObject object = (JSONObject) value;
                iterateJson(object, jsonMap, mappedKey);
            } else {
                jsonMap.put(mappedKey, value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" - ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean hasIntent() {
        return (get(Intent.INTENT) != null);
    }

    public Object get(Intent intent){
        return jsonMap.get(intent.getKey());
    }

    @Deprecated
    public Object get(String key){
        return jsonMap.get(key);
    }

    public void merge(WitResponse other) {
        jsonMap.putAll(other.jsonMap);
    }

    public void copy(Intent messagebody, Intent reminder) {
        Object object = jsonMap.get(messagebody.getKey());
        if (object == null) return;
        jsonMap.put(reminder.getKey(), object);
    }
}
