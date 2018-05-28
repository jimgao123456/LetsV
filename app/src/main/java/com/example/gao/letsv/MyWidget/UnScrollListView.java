package com.example.gao.letsv.MyWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by gangchang on 2018/5/28.
 */

public class UnScrollListView extends ListView {
    public UnScrollListView(Context context) {
        super(context);
    }

    public UnScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);//这里返回的是刚写好的<span style="font-family:Arial, Helvetica, sans-serif;">expandSpec </span>

    }
}
