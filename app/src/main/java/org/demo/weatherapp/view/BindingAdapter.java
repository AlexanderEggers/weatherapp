package org.demo.weatherapp.view;

import android.content.Context;
import android.graphics.Typeface;

public class BindingAdapter {

    private BindingAdapter() {
        //do nothing
    }

    public static Typeface setWeatherFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/weathericons-regular-webfont.ttf");
    }
}
