package org.demo.weatherapp.view;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.widget.TextView;

public class ViewAdapters {

    private ViewAdapters() {
        //do nothing
    }

    @BindingAdapter("setWeatherIcon")
    public static void setWeatherIcon(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(Html.fromHtml(value));
        }
    }
}
