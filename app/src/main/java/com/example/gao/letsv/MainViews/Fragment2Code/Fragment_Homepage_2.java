package com.example.gao.letsv.MainViews.Fragment2Code;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.gao.letsv.MyListAdatper.Fragment2_adapter;
import com.example.gao.letsv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_2 extends Fragment implements PopupWindow.OnDismissListener{

    private ListView listView;
    private List<Map<String, Object>> ItemList = new ArrayList<Map<String, Object>>();
    private String jsonString;
    private FloatingSearchView FloatingSearchViewReal = null;
    private FloatingSearchView FloatingSearchViewHidden = null;
    private  View curtainview=null;
    private String title_selected,url_selected;
    private String LastQuery = "";
    private static int screenHeight = 0;
    private PopupWindow popupWindow;
    private int navigationHeight;

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

        //开始FloatingSearch的108种时间设置
        //Home键监听
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
        //文本变动监听
        FloatingSearchViewHidden.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    FloatingSearchViewHidden.clearSuggestions();
                }else{
                    FloatingSearchViewHidden.showProgress();
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            250, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<mySuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    FloatingSearchViewHidden.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    FloatingSearchViewHidden.hideProgress();
                                }
                            });
                }
                Log.d("", "onSearchTextChanged()");
            }
        });
        //搜索监听
        FloatingSearchViewHidden.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                mySuggestion Suggestion = (mySuggestion) searchSuggestion;
                DataHelper.finditem(getActivity(), Suggestion.getBody(),
                        new DataHelper.OnFindListener() {

                            @Override
                            public void onResults(List<myWrapper> results) {
                                //show search results
                            }

                        });
                Log.d(TAG, "onSuggestionClicked()");

                LastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                LastQuery = query;

                DataHelper.finditem(getActivity(), query,
                        new DataHelper.OnFindListener() {

                            @Override
                            public void onResults(List<myWrapper> results) {
                                //show search results
                            }

                        });
                Log.d(TAG, "onSearchAction()");
            }
        });
        //焦点变动监听
        FloatingSearchViewHidden.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                FloatingSearchViewHidden.swapSuggestions(DataHelper.getHistory(getActivity(), 3));
                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
                FloatingSearchViewHidden.setSearchBarTitle(LastQuery);
                Log.d(TAG, "onFocusCleared()");
            }
        });


        FloatingSearchViewReal = view.findViewById(R.id.fragment2_floating_search_view_Real);

        //不知道什么监监听
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

        //获取设备高度
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationHeight = getResources().getDimensionPixelSize(resourceId);

        //读取列表
        init_item();
        Fragment2_adapter adapter = new Fragment2_adapter(getActivity(), R.layout.fragement2_list_item, ItemList);

        //listview点击监听
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                title_selected = (String)ItemList.get(i).get("title");
                url_selected = (String)ItemList.get(i).get("url");
                openPopupWindow(getView());
//                Intent intent = new Intent(getActivity(),activity_media_player.class);
//                intent.putExtra("title",title_selected);
//                intent.putExtra("url",url_selected);
//                startActivity(intent);
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

    //从服务器获得数据(POST没写)
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

    //打开弹出菜单
    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_choice, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().setAttributes(lp);
    }

    //popwindow点击监听
    private void setOnPopupViewClick(View view) {
        TextView poptitle, poplisten, popread,popcancel;
        poptitle = (TextView)view.findViewById(R.id.popupwin_title);
        poplisten = (TextView)view.findViewById(R.id.popupwin_listen);
        popread = (TextView)view.findViewById(R.id.popupwin_read);
        popcancel = (TextView)view.findViewById(R.id.popupwin_cancel);
        poptitle.setText(title_selected);
        //全文泛听点击监听
        poplisten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),activity_media_player.class);
                intent.putExtra("title",title_selected);
                intent.putExtra("url",url_selected);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        //跟读监听
        popread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),readActivity.class);
                intent.putExtra("title",title_selected);
                intent.putExtra("url",url_selected);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        //取消监听
        popcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    //实现dismiss接口
    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }
}