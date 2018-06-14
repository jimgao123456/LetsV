package com.example.gao.letsv.MainViews.Fragment2Code;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by xjl on 2018/6/14.
 */

public class mySuggestion implements SearchSuggestion{
    private String title;
    private boolean mIsHistory = false;

    public mySuggestion(String suggestion) {
        this.title = suggestion.toLowerCase();
    }

    public mySuggestion(Parcel source) {
        this.title = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return title;
    }

    public static final Creator<mySuggestion> CREATOR = new Creator<mySuggestion>() {
        @Override
        public mySuggestion createFromParcel(Parcel in) {
            return new mySuggestion(in);
        }

        @Override
        public mySuggestion[] newArray(int size) {
            return new mySuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
