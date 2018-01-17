package com.graction.developer.lumi.UI;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Graction06 on 2018-01-17.
 */

public class BindingAttributes {

    @BindingAdapter("ImageResource")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("ImageResource")
    public static void setImageResource(ImageView imageView, Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

}
