package com.ahaya.earthquakeviewer.Sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CompassView extends View {
    public CompassView(Context context) {
        this(context,null);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
