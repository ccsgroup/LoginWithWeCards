package com.wecardssdk.android.API.Handler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.wecardssdk.android.Utils.AppContacts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.wecardssdk.android.API.jsonKeys.DATA;
import static com.wecardssdk.android.API.jsonKeys.LOGIN_TOKEN;
import static com.wecardssdk.android.API.jsonKeys.MESSAGE;
import static com.wecardssdk.android.API.jsonKeys.RESULT;
import static com.wecardssdk.android.API.jsonKeys.STATUS;
import static com.wecardssdk.android.API.jsonKeys.SUCCESS;
import static com.wecardssdk.android.API.jsonKeys.USER_ID;
import static com.wecardssdk.android.Utils.AppContacts.PREFS_PRIVATE;
import static com.wecardssdk.android.Utils.errorHandler.showErrorHandler;


/**
 * Created by mauliksantoki on 26/4/17.
 */

public class APIResponseHandler extends APIResponse {

    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;

    private String apiResult = "";
    private String apiResultMessage = "";

    private SharedPreferences preferences;

    @Override
    public String getResult(Response<ResponseBody> reader) {

        bufferedReader = new BufferedReader(new InputStreamReader(reader.body().byteStream()));
        stringBuilder = new StringBuilder();

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return stringBuilder.toString();
    }

    @Override
    public void getData(Activity activity, String result, DataHandler handler) {
        try {
            if (preferences == null) {
                preferences = activity.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            }


            JSONObject jsonObject = new JSONObject(result).optJSONObject(RESULT);
            apiResultMessage = jsonObject.optString(MESSAGE);
            if (jsonObject.optString(STATUS).equalsIgnoreCase(SUCCESS)) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(USER_ID, jsonObject.getJSONObject(DATA).optString(USER_ID));
                editor.putString(LOGIN_TOKEN, jsonObject.getJSONObject(DATA).optString(LOGIN_TOKEN));
                editor.commit();

                apiResult = jsonObject.getJSONObject(DATA).toString();
                handler.getData(apiResult, apiResultMessage);
            } else {
                Toast.makeText(activity, apiResultMessage,
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showErrorHandler(activity, AppContacts.CONTACT_US);
        }
    }


}
