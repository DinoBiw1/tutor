package com.example.melodyhacker.tutor.Font;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


class CEditTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    int mystyle;

    public CEditTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface NormalFont = FontCache.getTypeface("font/Italic.ttf", context);
        setTypeface(NormalFont);

    }

}