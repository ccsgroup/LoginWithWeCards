package com.wecardssdk.android.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Validate {

    public static boolean isEmpty(String text) {
        text = text.trim();
        return text.isEmpty();
    }


    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


}
