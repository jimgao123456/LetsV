package com.example.gao.letsv.MyListAdatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gao.letsv.R;
import com.example.gao.letsv.fragment2ListItem;

import java.util.List;

/**
 * Created by xjl on 2018/5/31.
 */

public class Fragment2_adapter extends ArrayAdapter {
    private final int resourceId;

    public Fragment2_adapter(Context context, int textViewResourceId, List<fragment2ListItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fragment2ListItem item = (fragment2ListItem) getItem(position); // 获取当前项的item实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView title = (TextView) view.findViewById(R.id.fragment2_item_title);//获取该布局内的文本视图
        TextView date = (TextView) view.findViewById(R.id.fragment2_item_date);//获取该布局内的文本视图
        TextView url = (TextView) view.findViewById(R.id.fragment2_item_url);
        title.setText(item.getTitle());//为文本视图设置文本内容
        date.setText(item.getDate());
        url.setText(item.getUrl());
        return view;
    }
}
