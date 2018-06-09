package com.example.gao.letsv.MainViews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.LoginViews.activity_media_player;
import com.example.gao.letsv.MyListAdatper.Fragment2_adapter;
import com.example.gao.letsv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_2 extends Fragment {

    private ListView listView;
    private List<fragment2ListItem> ItemList = new ArrayList<fragment2ListItem>();
    private String jsonString;
    public Fragment_Homepage_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_2, container, false);
        listView =(ListView) listView.findViewById(R.id.fragment2_list_view);
        init_item();
        Fragment2_adapter adapter = new Fragment2_adapter(getActivity(),R.layout.fragement2_list_item,ItemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position 点击的Item位置，从0开始算
                Intent intent=new Intent(getActivity(),activity_media_player.class);
                intent.putExtra("url",ItemList.get(position).getUrl());//传递给下一个Activity的值
                intent.putExtra("title",ItemList.get(position).getTitle());//传递给下一个Activity的值
                startActivity(intent);//启动Activity
            }
        });
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

    private void init_item() {
        //发送请求
        JSONArray jsonArray = GetData();
        read_json(GetData());
        Log.i("解读ok", jsonString);
        //添加数据
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            ItemList.add(new fragment2ListItem((String) jsonObject.getString("title"), (String) jsonObject.getString("date"),
                    (String) jsonObject.getString("url")));
//        fragment2ListItem one= new fragment2ListItem("我就瞎几把测一测哈哈哈哈哈哈哈哈哈", "2016-2-12");
//        ItemList.add(one);
        }
    }

    //TODO:从服务器获得数据
    private JSONArray GetData(){
        //TODO:post请求没写，需需要协助；
        String  JSON_ARRAY_STR = "[{\"title\":\"lily\",\"date\":2018-06-01,\"url\",:www.baidu,com},{\"title\":\"lucy\",\"date\":2016-06-02,\"url\",:www.baidu,com}]";
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        return jsonArray;
    }

    //TODO：解读json数组，
    /**
     * json格式
     * 标题：XXX String
     * 日期：2017-16-XX String
     */
    private void read_json(JSONArray jsonArray){
        jsonString = JSONArray.toJSONString(jsonArray);
    }
}
