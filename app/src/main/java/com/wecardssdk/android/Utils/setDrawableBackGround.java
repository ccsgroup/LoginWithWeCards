package com.wecardssdk.android.Utils;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by mauliksantoki on 25/4/17.
 */

public class setDrawableBackGround {
    public static GradientDrawable setDrawableBackGround(int bgcolor, int brdcolor, int borderStroke, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius) {

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(bgcolor);
        gdDefault.setStroke(borderStroke, brdcolor);
        gdDefault.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});

        return gdDefault;
    }
}
