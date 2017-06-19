package com.wecardssdk.android.Controller.Handler;

import android.support.annotation.Keep;

/**
 * Created by mauliksantoki on 24/4/17.
 */
@Keep
public interface LoginHandler {

    public void loginResult(String Result);

    public void logoutResult(String Result);


}
