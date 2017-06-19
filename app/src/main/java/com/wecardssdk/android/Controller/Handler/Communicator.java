package com.wecardssdk.android.Controller.Handler;

import android.app.Activity;

/**
 * Created by mauliksantoki on 26/4/17.
 */

public abstract class Communicator {

    public abstract void connect(Activity activity, LoginHandler loginHandler);

    public abstract void disConnect(Activity activity);

    public abstract void logout(Activity activity);

    public abstract void logoutAPI();

    public abstract void showProgress();


    public abstract void hideProgress();

}
