package com.wecardssdk.android.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by mauliksantoki on 25/4/17.
 */

public class Deprecation {

    public static final int getColor(Activity activity, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getColor(id);
        } else {
            return activity.getResources().getColor(id);
        }
    }

    public static final int getColor(Context activity, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getColor(id);
        } else {
            return activity.getResources().getColor(id);
        }
    }

    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
