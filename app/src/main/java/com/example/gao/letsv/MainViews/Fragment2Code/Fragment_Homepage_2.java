package com.example.gao.letsv.MainViews.Fragment2Code;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.baoyz.actionsheet.ActionSheet;
import com.example.gao.letsv.MainViews.Fragment1Code.Painting;
import com.example.gao.letsv.MainViews.Fragment1Code.PaintingsAdapter;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.MyListAdatper.Fragment2_adapter;
import com.example.gao.letsv.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_2 extends Fragment {

    private static String requstrul = MainActivity.serverip + "audiolist";
    private View rootView;
    private ListView listView;
    private String jsonString;
    private FloatingSearchView FloatingSearchViewReal = null;

    private String LastQuery = "";

    private static boolean finishload = false;

    public Fragment_Homepage_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView) {
            View view = inflater.inflate(R.layout.fragment_homepage_2, null);
            rootView = view;
            if (getUserVisibleHint()) {
                initView(view);
                init_listitem(rootView);
            }
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        /**
         * 判断此Fragment是否正在前台显示
         * 通过判断就知道是否要进行数据加载了
         */
        if (isVisibleToUser && isVisible()) {
            if (!finishload) {
                initView(rootView);
                init_listitem(rootView);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.fragment2_list_view);
        FloatingSearchViewReal = (FloatingSearchView)view.findViewById(R.id.fragment2_floating_search_view_Real);


        //Home键监听
        FloatingSearchViewReal.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
            }
        });
        //文本变动监听
        FloatingSearchViewReal.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    FloatingSearchViewReal.clearSuggestions();
                } else {
                    FloatingSearchViewReal.showProgress();
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            250, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<mySuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    FloatingSearchViewReal.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    FloatingSearchViewReal.hideProgress();
                                }
                            });
                }
            }
        });
        //搜索监听
        FloatingSearchViewReal.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
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
        FloatingSearchViewReal.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                FloatingSearchViewReal.swapSuggestions(DataHelper.getHistory(getActivity(), 3));
            }

            @Override
            public void onFocusCleared() {
                FloatingSearchViewReal.setSearchBarTitle(LastQuery);
            }
        });

    }

    private void init_listitem(View view) {
        //发送请求
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在拉取听说列表");
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient(8888);
        RequestParams params = new RequestParams();
        //params.put("audioId", word);
        String url = requstrul;
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                JSONObject respondsjson = JSON.parseObject(str);
                int step = 0;
                List<Map<String, Object>> ItemList = new ArrayList<Map<String, Object>>();
                while (true) {

                    if ((respondsjson.getString(Integer.toString(step))) != null) {
                        JSONObject jsonObject = JSONObject.parseObject(respondsjson.getString(Integer.toString(step)));

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("date", jsonObject.getString("date"));
                        map.put("imageUrl", jsonObject.getString("imageUrl"));
                        map.put("audioId", jsonObject.getString("audioId"));
                        map.put("title", jsonObject.getString("title"));

                        ItemList.add(map);
                        step++;
                    } else {
                        break;
                    }
                }
                Fragment2_adapter adapter = new Fragment2_adapter(getActivity(), R.layout.fragement2_list_item, ItemList);
                listView.setAdapter(adapter);

                //listview点击监听
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String title_selected = (String) adapter.getItem(i).get("title");
                        String AudioID = (String)adapter.getItem(i).get("audioId");
                        SweetAlertDialog pDialog1 = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                        pDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog1.setTitleText("正在拉取音频");
                        pDialog1.setCancelable(false);
                        pDialog1.show();

                        AsyncHttpClient client = new AsyncHttpClient(8888);
                        RequestParams params = new RequestParams();
                        params.put("audioid",AudioID);
                        String url = MainActivity.serverip+"audio";
                        client.post(url, params, new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String str1 = new String(responseBody);
                                JSONObject respondsjson1 = JSON.parseObject(str1);
                                String audioUrl =respondsjson1.getString("audioUrl");
                                //播放音频url
                               String url_selected = MainActivity.serverip+audioUrl;

                                ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                                        .setCancelButtonTitle("取消")
                                        .setOtherButtonTitles("全文泛听", "语音跟读")
                                        .setCancelableOnTouchOutside(true)
                                        .setListener(new ActionSheet.ActionSheetListener() {
                                            @Override
                                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                                            }

                                            @Override
                                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                                switch (index) {
                                                    case 0:
                                                        //全文泛听
                                                        Intent intent_quanwen = new Intent(getActivity(), activity_media_player.class);
                                                        intent_quanwen.putExtra("title", title_selected);
                                                        intent_quanwen.putExtra("url", url_selected);
                                                        startActivity(intent_quanwen);
                                                        break;
                                                    case 1:
                                                        //语音跟读
                                                        Intent intent_gendu = new Intent(getActivity(), readActivity.class);
                                                        intent_gendu.putExtra("title", title_selected);
                                                        intent_gendu.putExtra("url", url_selected);
                                                        startActivity(intent_gendu);
                                                        break;
                                                }
                                            }
                                        }).show();
                                pDialog1.cancel();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers,
                                                  byte[] responseBody, Throwable error) {
                                pDialog1.setTitleText("未知错误")
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }
                        });

                    }
                });

                pDialog.cancel();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                pDialog.setTitleText("未知错误")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });

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

}