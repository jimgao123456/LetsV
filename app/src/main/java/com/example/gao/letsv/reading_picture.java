package com.example.gao.letsv;


import cz.msebera.android.httpclient.client.cache.Resource;
import android.content.res.Resources;
import android.content.res.TypedArray;

/**
 * Created by dk150 on 2018/6/5.
 */

public class reading_picture {
    private final int picture;
    private final String title;
    private final String detail;

    private reading_picture(int picture, String title, String detail) {
        this.picture = picture;
        this.title = title;
        this.detail = detail;
    }

    public int getPicture() {
        return picture;
    }

    public String getPicTitle() {
        return title;
    }

    public String getPicDetails() {
        return detail;
    }

    public static reading_picture[] getAllPicture(Resources res) {
        String[] titles = res.getStringArray(R.array.pic_title);
        String[] details = res.getStringArray(R.array.pic_details);
        TypedArray pictures = res.obtainTypedArray(R.array.picture);

        int size = titles.length;
        reading_picture[] RP = new reading_picture[size];
        for(int i = 0;i<size;i++){
            final int picture = pictures.getResourceId(i,-1);
            RP[i]=new reading_picture(picture,titles[i],details[i]);
        }

        pictures.recycle();

        return RP;
    }
}
