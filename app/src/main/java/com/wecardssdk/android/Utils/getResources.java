package com.wecardssdk.android.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

/**
 * Created by mauliksantoki on 25/4/17.
 */

public class getResources {


    public static Drawable getDrawable(Context context, String resource_name) {
        try {
            int resId = context.getResources().getIdentifier(resource_name, "mipmap", context.getPackageName());
            if (resId != 0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return context.getResources().getDrawable(resId, context.getTheme());
                } else {
                    return context.getResources().getDrawable(resId);
                }
            }
        } catch (Exception e) {
            Log.e(AppContacts.Tag, "getDrawable - resource_name: " + resource_name);
            e.printStackTrace();
        }

        return null;
    }


    public static String getString(Context context, String resource_name) {
        try {
            int resId = context.getResources().getIdentifier(resource_name, "string", context.getPackageName());
            if (resId != 0) {
                return context.getResources().getString(resId);
            }
        } catch (Exception e) {
            Log.e(AppContacts.Tag, "getDrawable - resource_name: " + resource_name);
            e.printStackTrace();
        }

        return "";
    }
}
