package com.example.gao.letsv.MainViews.Fragment2Code;

import java.util.Comparator;

/**
 * Created by xjl on 2018/6/18.
 */
class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        Float a,b;
        a = Float.parseFloat(str1);
        b = Float.parseFloat(str2);
        return a.compareTo(b);
    }
}
