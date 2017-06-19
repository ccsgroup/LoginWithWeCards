package com.wecardssdk.android.Controller.Handler;

/**
 * Created by mauliksantoki on 25/4/17.
 */

public interface loginWithWecardsHandler {
    public void setAppPackageName();

    public void setAppIcon();

    public void setAppName();

    public void setAppLoginID();


    public void login();

    public void logout();

    public void unRegisterCommunicator();

    public boolean validation();

    public void error(String message);



}
