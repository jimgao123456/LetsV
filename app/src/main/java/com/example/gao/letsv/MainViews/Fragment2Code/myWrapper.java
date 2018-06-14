package com.example.gao.letsv.MainViews.Fragment2Code;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xjl on 2018/6/14.
 */

public class myWrapper implements Parcelable {
    private String title;
    private String url;
    private String date;

    private myWrapper(Parcel in) {
        title = in.readString();
        url = in.readString();
        date = in.readString();
    }

    public myWrapper(String title){
        this.title = title;
        this.url = "";
        this.date = "";
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final Creator<myWrapper> CREATOR = new Creator<myWrapper>() {
        @Override
        public myWrapper createFromParcel(Parcel in) {
            return new myWrapper(in);
        }

        @Override
        public myWrapper[] newArray(int size) {
            return new myWrapper[size];
        }
    };
}
