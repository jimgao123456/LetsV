package com.example.gao.letsv;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by dk150 on 2018/6/6.
 */

/**
 * 加载图片，留着用。附带缓存功能。
 */

public class GlideHelper {
    private GlideHelper() {}
    public static void loadPicImage(ImageView image, reading_picture reading_picture) {
        Glide.with(image.getContext().getApplicationContext())
                .load(reading_picture.getPicture())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }
}