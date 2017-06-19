package com.wecardssdk.android.CustomView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;

import com.wecardssdk.android.R;
import com.wecardssdk.android.Utils.AppTypeface;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;


/**
 * Created by bhavika on 5/9/16.
 */

public class DialogProgress extends ProgressDialog {
    private AppTypeface typeface;
    private final String message = "Please Wait ...";

    public DialogProgress(Activity context) {
        super(context, getStyle());

        typeface = new AppTypeface(context);
        setCancelable(false);

        SpannableString spannableString = new SpannableString(message);
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(typeface.getRegularFont());
        spannableString.setSpan(typefaceSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setMessage(spannableString);
    }

    private static int getStyle() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return R.style.AppAlertTheme;
        } else {
            return R.style.AppAlertTheme19;
        }
    }
}
