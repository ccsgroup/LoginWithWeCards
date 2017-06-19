package com.wecardssdk.android.Controller.Handler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import com.wecardssdk.android.API.Handler.APIResponse;
import com.wecardssdk.android.API.Handler.APIResponseHandler;
import com.wecardssdk.android.API.RestClient;
import com.wecardssdk.android.CustomView.DialogProgress;
import com.wecardssdk.android.Model.LogoutRequest;
import com.wecardssdk.android.Utils.AppContacts;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wecardssdk.android.API.jsonKeys.DATA;
import static com.wecardssdk.android.API.jsonKeys.LOGIN_TOKEN;
import static com.wecardssdk.android.API.jsonKeys.USER_ID;
import static com.wecardssdk.android.Utils.AppContacts.PREFS_PRIVATE;
import static com.wecardssdk.android.Utils.AppContacts.Tag;
import static com.wecardssdk.android.Utils.AppContacts.loginWithAppPackageName;
import static com.wecardssdk.android.Utils.AppContacts.loginWithWecardKey;

/**
 * Created by mauliksantoki on 26/4/17.
 */

public class CommunicatorHandler extends Communicator {
    private BroadcastReceiver loginWidthWecardsReceiver;
    IntentFilter filter;

    private Activity activity;

    private SharedPreferences preferences;
    private LoginHandler loginHandler;

    private DialogProgress progressDialogCustom;

    private LogoutRequest logoutRequest;
    private APIResponse apiResponseHandler;

    private JSONObject jsonObject;

    @Override
    public void connect(Activity activity, final LoginHandler loginHandler) {
        this.activity = activity;
        filter = new IntentFilter();
        filter.addAction(loginWithWecardKey);
        this.loginHandler = loginHandler;
        preferences = activity.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        loginWidthWecardsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (loginHandler != null) {
                        SharedPreferences.Editor editor = preferences.edit();
                        jsonObject = new JSONObject(intent.getStringExtra(DATA));
                        editor.putString(USER_ID, jsonObject.optString(USER_ID));
                        editor.putString(LOGIN_TOKEN, jsonObject.optString(LOGIN_TOKEN));
                        editor.commit();
                        loginHandler.loginResult(jsonObject.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(AppContacts.Tag, "Contact to ");
                }
            }
        };
        activity.registerReceiver(loginWidthWecardsReceiver, filter);
    }

    @Override
    public void disConnect(Activity activity) {
        activity.unregisterReceiver(loginWidthWecardsReceiver);
    }

    @Override
    public void logout(Activity activity) {
        if (preferences == null) {
            preferences = activity.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        }
        logoutRequest = new LogoutRequest();
        logoutRequest.setApi_key(loginWithWecardKey);
        logoutRequest.setPackagename(loginWithAppPackageName);
        logoutRequest.setLogin_token(preferences.getString(LOGIN_TOKEN, ""));
        logoutRequest.setUser_id(preferences.getString(USER_ID, ""));
        logoutAPI();
    }

    @Override
    public void logoutAPI() {
        showProgress();
        if (apiResponseHandler == null) {
            apiResponseHandler = new APIResponseHandler();
        }

        RestClient.GitApiInterface service = RestClient.getClient();
        Call<ResponseBody> call = service.logout(logoutRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loginHandler.logoutResult(apiResponseHandler.getResult(response));
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(USER_ID, "");
                    editor.putString(LOGIN_TOKEN, "");
                    editor.commit();
                } else {
                    Log.e(Tag, "fail" + response);
                }


                hideProgress();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Tag, t.getMessage());
                hideProgress();
            }
        });


    }

    @Override
    public void showProgress() {
        if (progressDialogCustom == null) {
            progressDialogCustom = new DialogProgress(activity);


        }
        progressDialogCustom.show();
    }

    @Override
    public void hideProgress() {

        if (progressDialogCustom != null) {
            progressDialogCustom.hide();
        }

    }
}
