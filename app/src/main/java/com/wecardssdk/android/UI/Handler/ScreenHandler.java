package com.wecardssdk.android.UI.Handler;

/**
 * Created by mauliksantoki on 25/4/17.
 */

public interface ScreenHandler {

    public void initControl();

    public void initDailogHeight();

    public void initlayouts();

    public void initTypeFace();

    public void initClickListener();

    public void APICall();

    public void hideSoftInput();

    public void openAppInPlayStore();

    public void showProgress();

    public void hideProgress();

    public boolean validation();

    public void error(String message);

    public void scrollUpDialog();
}
