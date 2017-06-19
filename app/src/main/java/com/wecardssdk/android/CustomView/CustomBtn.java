package com.wecardssdk.android.CustomView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.wecardssdk.android.R;

import static com.wecardssdk.android.Utils.setDrawableBackGround.setDrawableBackGround;


/**
 * Created by mauliksantoki on 10/1/17.
 */

public class CustomBtn extends android.support.v7.widget.AppCompatButton {

    int deadultColor;
    int pressColor;
    int borderCOlor = 8;

    int borderStroke = 8;
    int leftTopRadius = 8;
    int rightTopRadius = 8;
    int rightBottomRadius = 8;
    int leftBottomRadius = 8;

    int textColor;
    int textPressColor;

    StateListDrawable states;

    public CustomBtn(Context context) {
        super(context);
    }

    public CustomBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomBtn,
                0, 0);

        deadultColor = a.getColor(R.styleable.CustomBtn_btnDeafultColor, context.getResources().getColor(R.color.black));
        pressColor = a.getColor(R.styleable.CustomBtn_btnPressColor, context.getResources().getColor(R.color.black));
        borderCOlor = a.getColor(R.styleable.CustomBtn_btnBorderCOlor, 0);

        textColor = a.getColor(R.styleable.CustomBtn_btnTextColor, context.getResources().getColor(R.color.black));
        textPressColor = a.getColor(R.styleable.CustomBtn_btnTextPress, context.getResources().getColor(R.color.black));

        borderStroke = a.getColor(R.styleable.CustomBtn_btnBorderSize, 0);
        leftTopRadius = a.getColor(R.styleable.CustomBtn_btnCornerRadiusLeftTop, 0);
        rightTopRadius = a.getColor(R.styleable.CustomBtn_btnCornerRadiusRightTop, 0);
        rightBottomRadius = a.getColor(R.styleable.CustomBtn_btnCornerRadiusRightBottom, 0);
        leftBottomRadius = a.getColor(R.styleable.CustomBtn_btnCornerRadiusLeftBottom, 0);

        setBackGroundStates();
    }

    public CustomBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setRadius(int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.rightBottomRadius = rightBottomRadius;
        this.leftBottomRadius = leftBottomRadius;
        setBackGroundStates();
    }

    private void setBackGroundStates() {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, setDrawableBackGround(pressColor, borderCOlor, borderStroke, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        states.addState(new int[]{android.R.attr.state_selected}, setDrawableBackGround(pressColor, borderCOlor, borderStroke, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        states.addState(new int[]{}, setDrawableBackGround(deadultColor, borderCOlor, borderStroke, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        setBackground(states);

        int[][] colorStates = new int[][]{
                new int[]{}, // enabled
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                textColor, textPressColor
        };

        ColorStateList colorStateList = new ColorStateList(colorStates, colors);
        setTextColor(colorStateList);
    }

    public int getDeadultColor() {
        return deadultColor;
    }

    public void setDeadultColor(int deadultColor) {
        this.deadultColor = deadultColor;
    }

    public int getPressColor() {
        return pressColor;
    }

    public void setPressColor(int pressColor) {
        this.pressColor = pressColor;
    }

    public int getBorderCOlor() {
        return borderCOlor;
    }

    public void setBorderCOlor(int borderCOlor) {
        this.borderCOlor = borderCOlor;
    }

    public int getBorderStroke() {
        return borderStroke;
    }

    public void setBorderStroke(int borderStroke) {
        this.borderStroke = borderStroke;
    }

    public int getLeftTopRadius() {
        return leftTopRadius;
    }

    public void setLeftTopRadius(int leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
    }

    public int getRightTopRadius() {
        return rightTopRadius;
    }

    public void setRightTopRadius(int rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
    }

    public int getRightBottomRadius() {
        return rightBottomRadius;
    }

    public void setRightBottomRadius(int rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
    }

    public int getLeftBottomRadius() {
        return leftBottomRadius;
    }

    public void setLeftBottomRadius(int leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
    }

    public int getTextColor() {
        return textColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public void setTextPressColor(int textPressColor) {
        this.textPressColor = textPressColor;
    }

    public StateListDrawable getStates() {
        return states;
    }

    public void setStates(StateListDrawable states) {
        this.states = states;
    }



}
