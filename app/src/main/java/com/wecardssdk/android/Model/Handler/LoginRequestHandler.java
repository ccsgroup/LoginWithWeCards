package com.wecardssdk.android.Model.Handler;

import android.app.Activity;

import com.wecardssdk.android.Model.LoginRequest;


/**
 * Created by mauliksantoki on 26/4/17.
 */

public abstract class LoginRequestHandler {

    public abstract LoginRequest getRequestData();

    public abstract void loginCompleted(Activity activity, String result);
}
