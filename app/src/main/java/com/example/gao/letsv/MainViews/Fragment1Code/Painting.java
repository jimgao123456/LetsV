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
   public   String context;
    private final  String wordNumber;
private  final  String passageId;
private final String type;
private final  String Date;


    public  Painting(String imageurl, String title,String wordNumber,String passageId,String type,String Date) {

        this.imageurl = imageurl;
        this.title = title;
        this.wordNumber=wordNumber;
        this.passageId=passageId;
        this.type=type;
        this.Date=Date;
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

    public String getPassageId() {
        return passageId;
    }

    public String getwordNumber() {
        return wordNumber;
    }

    public String getDate() {
        return Date;
    }

    public String getType() {
        return type;
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