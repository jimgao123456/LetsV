package com.example.gao.letsv.MainViews.Fragment1Code;

/**
 * Created by gangchang on 2018/6/13.
 */
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideHelper {

    private GlideHelper() {}

    public static void loadPaintingImage(ImageView image, Painting painting) {
        Glide.with(image.getContext().getApplicationContext())
                .load(painting.getImageId())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }

}