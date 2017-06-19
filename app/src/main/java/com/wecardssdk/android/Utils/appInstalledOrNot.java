package com.wecardssdk.android.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by mauliksantoki on 24/4/17.
 * <p>
 * this code is use fro check app install in phone or not when app install then return true other wise returen false..
 */

public class appInstalledOrNot {

    public static boolean appInstalledOrNot(String packagename, Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
            /*we add login with wecards in version code 5 ,version 1.0.4*/
            if (packageInfo.versionCode > 4) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(AppContacts.Tag, e.getMessage());

        }
        return false;
    }
}
