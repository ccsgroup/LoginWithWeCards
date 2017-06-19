package com.wecardssdk.android.Utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mauliksantoki on 29/4/17.
 */

public class errorHandler {

    public static void showErrorHandler(Activity activity, String message) {
        Log.e(AppContacts.Tag, message);
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
