package com.example.gao.letsv.MyListAdatper;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gao.letsv.R;

import java.util.List;
import java.util.Map;

/**
 * Created by xjl on 2018/5/31.
 */

public class Fragment2_adapter extends ArrayAdapter {
    private int resourceId;
    private Context context;
    private   List<Map<String, Object>> dataList;

    public Fragment2_adapter(Context context, int textViewResourceId,  List<Map<String, Object>>  objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.context=context;
        dataList=objects;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        fragment2ListItem util=null;
        if (view == null) {
            util = new fragment2ListItem();
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resourceId, null);
            // 指向布局文件内部组件
            util.title = (TextView) view
                    .findViewById(R.id.fragment2_item_title);
            util.data = (TextView) view.findViewById(R.id.fragment2_item_date);

            util.url = (TextView) view.findViewById(R.id.fragment2_item_url);
            // 增加额外变量
            view.setTag(util);
        } else {
            util = (fragment2ListItem) view.getTag();
        }

//        fragment2ListItem item = (fragment2ListItem) getItem(position); // 获取当前项的item实例
//        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
//        TextView title = (TextView) view.findViewById(R.id.fragment2_item_title);//获取该布局内的文本视图
//        TextView date = (TextView) view.findViewById(R.id.fragment2_item_date);//获取该布局内的文本视图
//        TextView url = (TextView) view.findViewById(R.id.fragment2_item_url);
//        title.setText(item.getTitle());//为文本视图设置文本内容
//        date.setText(item.getDate());
//        url.setText(item.getUrl());

        // 获取数据显示在各组件
        Map<String, Object> map = dataList.get(position);
        util.title.setText((String) map.get("title"));
        util.data.setText((String)map.get("data"));
        util.url.setText((String) map.get("url"));
        return view;
    }

    /**
     * 内部类，用于辅助适配
     */
    class fragment2ListItem {
        TextView title,data,url;
    }
}
