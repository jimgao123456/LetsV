package com.example.gao.letsv.MainViews.Fragment2Code;

/**
 * Created by xjl on 2018/6/14.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {

    private static List<myWrapper> Wrappers = new ArrayList<>();

    private static List<mySuggestion> Suggestions =
            new ArrayList<>(Arrays.asList(
                    new mySuggestion("gay"),
                    new mySuggestion("fuck you")
            ));

    public interface OnFindListener {
        void onResults(List<myWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<mySuggestion> results);
    }

    public static List<mySuggestion> getHistory(Context context, int count) {

        List<mySuggestion> suggestionList = new ArrayList<>();
        mySuggestion Suggestion;
        for (int i = 0; i < Suggestions.size(); i++) {
            Suggestion = Suggestions.get(i);
            Suggestion.setIsHistory(true);
            suggestionList.add(Suggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (mySuggestion Suggestion : Suggestions) {
            Suggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<mySuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (mySuggestion suggestion : Suggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<mySuggestion>() {
                    @Override
                    public int compare(mySuggestion lhs, mySuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<mySuggestion>) results.values);
                }
            }
        }.filter(query);

    }


    public static void finditem(Context context, String query, final OnFindListener listener) {
        initWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<myWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (myWrapper color : Wrappers) {
                        if (color.getUrl().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(color);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<myWrapper>) results.values);
                }
            }
        }.filter(query);

    }

    private static void initWrapperList(Context context) {

        if (Wrappers.isEmpty()) {
            JSONArray jsonString = loadJson(context);
            Wrappers = deserializeColors(jsonString);
        }
    }

    private static JSONArray loadJson(Context context) {
        String jsonString="[{\"title\":\"lily\",\"data\":\"2018-06-01\",\"url\":\"http://ww.danmu.fm:233/%E3%81%84%E3%81%91%E3%81%AA%E3%81%84%E3%83%9C%E3%83%BC%E3%83%80%E3%83%BC%E3%83%A9%E3%82%A4%E3%83%B3.m4a\"},{\"title\":\"lucy\",\"data\":\"2016-06-02\",\"url\":\"www.baidu,com\"}]";
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        return jsonArray;
    }

    private static List<myWrapper> deserializeColors(JSONArray jsonArray) {
        List<myWrapper> mylist = new ArrayList<>();
        //添加数据
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("title", jsonObject.getString("title"));
//            map.put("data", jsonObject.getString("data"));
//            map.put("url", jsonObject.getString("url"));
//            ItemList.add(map);
            mylist.add(new myWrapper(jsonObject.getString("title")));
        }
        return mylist;
    }
}