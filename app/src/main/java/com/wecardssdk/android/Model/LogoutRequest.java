package com.wecardssdk.android.Model;

import android.support.annotation.Keep;

/**
 * Created by mauliksantoki on 27/4/17.
 */
@Keep
public class LogoutRequest {
    private String api_key;

    private String packagename;

    private String user_id;

    private String login_token;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    @Override
    public String toString() {
        return "ClassPojo [api_key = " + api_key + ", packagename = " + packagename + ", user_id = " + user_id + ", login_token = " + login_token + "]";
    }
}
