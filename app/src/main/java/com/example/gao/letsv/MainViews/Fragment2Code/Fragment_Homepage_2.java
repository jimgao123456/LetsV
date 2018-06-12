package com.example.gao.letsv.MainViews.Fragment2Code;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.gao.letsv.MyListAdatper.Fragment2_adapter;
import com.example.gao.letsv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_2 extends Fragment {

    private ListView listView;
    private List<Map<String, Object>> ItemList = new ArrayList<Map<String, Object>>();
    private String jsonString;
    private FloatingSearchView FloatingSearchViewReal = null;
    private FloatingSearchView FloatingSearchViewHidden = null;
    private  View curtainview=null;
    private static int screenHeight = 0;

    public Fragment_Homepage_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_2, container, false);
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenHeight = outMetrics.heightPixels;
        listView = (ListView) view.findViewById(R.id.fragment2_list_view);
        curtainview=(View)view.findViewById(R.id.fragment2_floating_search_view_Real_mm);
        FloatingSearchViewHidden = view.findViewById(R.id.fragment2_floating_search_view_Hidden);
        FloatingSearchViewHidden.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                ViewGroup.LayoutParams params = FloatingSearchViewHidden.getLayoutParams();
                params.height = 0;
                FloatingSearchViewHidden.setLayoutParams(params);
                FloatingSearchViewReal.setVisibility(View.VISIBLE);
                curtainview.setVisibility(View.VISIBLE);
            }
        });
        FloatingSearchViewReal = view.findViewById(R.id.fragment2_floating_search_view_Real);
        curtainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int screenWidth = outMetrics.widthPixels;
                ViewGroup.LayoutParams params = FloatingSearchViewHidden.getLayoutParams();
                params.height = screenHeight;
                FloatingSearchViewHidden.setLayoutParams(params);
                FloatingSearchViewReal.setVisibility(View.INVISIBLE);
                curtainview.setVisibility(View.INVISIBLE);
            }
        });
        init_item();
        Fragment2_adapter adapter = new Fragment2_adapter(getActivity(), R.layout.fragement2_list_item, ItemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title_selected = (String)ItemList.get(i).get("title");
                String url_selected = (String)ItemList.get(i).get("url");
                Intent intent = new Intent(getActivity(),activity_media_player.class);
                intent.putExtra("title",title_selected);
                intent.putExtra("url",url_selected);
                startActivity(intent);
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
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", jsonObject.getString("title"));
            map.put("data", jsonObject.getString("data"));
            map.put("url", jsonObject.getString("url"));
            ItemList.add(map);
//
//            ItemList.add(new fragment2ListItem((String) jsonObject.getString("title"), (String) jsonObject.getString("date"),
//                    (String) jsonObject.getString("url")));
//        fragment2ListItem one= new fragment2ListItem("我就瞎几把测一测哈哈哈哈哈哈哈哈哈", "2016-2-12");
//        ItemList.add(one);
        }
    }

    //从服务器获得数据
    private JSONArray GetData() {
        //post请求没写，需需要协助；
        /*
        *
         */
        String JSON_ARRAY_STR = "[{\"title\":\"lily\",\"data\":\"2018-06-01\",\"url\":\"http://ww.danmu.fm:233/%E3%81%84%E3%81%91%E3%81%AA%E3%81%84%E3%83%9C%E3%83%BC%E3%83%80%E3%83%BC%E3%83%A9%E3%82%A4%E3%83%B3.m4a\"},{\"title\":\"lucy\",\"data\":\"2016-06-02\",\"url\":\"www.baidu,com\"}]";
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        return jsonArray;
    }

    //解读json数组，

    /**
     * json格式
     * 标题：XXX String
     * 日期：2017-16-XX String
     */
    private void read_json(JSONArray jsonArray) {
        jsonString = JSONArray.toJSONString(jsonArray);
    }
}
