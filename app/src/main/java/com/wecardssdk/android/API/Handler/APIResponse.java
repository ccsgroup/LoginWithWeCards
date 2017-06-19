package com.wecardssdk.android.API.Handler;

import android.app.Activity;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by mauliksantoki on 26/4/17.
 */

public abstract class APIResponse {

    public abstract String getResult(Response<ResponseBody> reader);

    public abstract void getData(Activity activity, String result, DataHandler handler);

}
