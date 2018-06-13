package com.example.gao.letsv.MainViews.Fragment1Code;

/**
 * Created by gangchang on 2018/6/13.
 */

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.gao.letsv.R;


public class Painting {

    private final String imageurl;
    private final String title;
    private final  String context;

    public  Painting(String imageurl, String title, String context) {
        this.imageurl = imageurl;
        this.title = title;
        this.context=context;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }


    public static Painting[] getAllPaintings(Resources res) {
//        String[] titles = res.getStringArray(R.array.paintings_titles);
//        String[] years = res.getStringArray(R.array.paintings_years);
//        String[] locations = res.getStringArray(R.array.paintings_locations);
//        TypedArray images = res.obtainTypedArray(R.array.paintings_images);
//
//        int size = titles.length;
//        Painting[] paintings = new Painting[size];
//
//        for (int i = 0; i < size; i++) {
//            final int imageId = images.getResourceId(i, -1);
//            paintings[i] = new Painting(imageId, titles[i], years[i], locations[i]);
//        }
//
//        images.recycle();
//
//        return paintings;
        return  null;
    }

}