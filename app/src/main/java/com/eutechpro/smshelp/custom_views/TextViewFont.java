package com.eutechpro.smshelp.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.eutechpro.smshelp.R;


public class TextViewFont extends android.support.v7.widget.AppCompatTextView {
    private String fontName;

    public TextViewFont(Context context) {
        super(context);
    }

    public TextViewFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        getCustomStylesValues(context, attrs);

        if (fontName != null && !fontName.isEmpty()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), String.format("fonts/%s", fontName));
            setTypeface(typeface);
        }
    }

    private void getCustomStylesValues(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextViewFont,
                0, 0);

        try {
            fontName = a.getString(R.styleable.TextViewFont_font);
        } finally {
            a.recycle();
        }
    }

    @SuppressWarnings("unused")
    public void setText(String tekst) {
        super.setText(tekst);
        setBackgroundColor(Color.TRANSPARENT);
    }
}
