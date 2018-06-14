


package com.example.gao.letsv.MainViews.Fragment1Code;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.MyListAdatper.MyAdapter_study_main;
import com.example.gao.letsv.R;
import com.example.gao.letsv.Studyword.activity_study_word_main;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class Fragment_Homepage_1 extends Fragment {

    private ListView listView;
    private static String requstrul = MainActivity.serverip + "passagelist";
    private View rootView;
    private static boolean finishload = false;
    private TextView unfoldTextview = null;


    //滑动处理相关
    private int mMove;
    private int yDown, yMove;
    private int i = 0;
    private static int unfoldtextheigh = 0;
    private int unfoldtext_positonY_min = 0;
    private int unfoldtext_positonY_max = 0;

    public Fragment_Homepage_1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView) {
            View view = inflater.inflate(R.layout.fragment_homepage_1, null);
            rootView = view;
            if (getUserVisibleHint()) {
                initView(view);
                inilistItem(view);
            }
            MainActivity.MyTouchListener myTouchListener = new MainActivity.MyTouchListener() {
                @Override
                public void onTouchEvent(MotionEvent event) {
                    if (finishload) {
                        int y = (int) event.getY();
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                yDown = y;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                    }
                }
            };

            // 将myTouchListener注册到分发列表
            ((MainActivity) this.getActivity()).registerMyTouchListener(myTouchListener);
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
                inilistItem(rootView);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //在这里获取View及其子控件的坐标和长宽信息
                unfoldtext_positonY_min = (int) unfoldTextview.getY();
                unfoldtext_positonY_max = (int) unfoldTextview.getY() + unfoldTextview.getHeight();
            }
        });
        unfoldTextview = view.findViewById(R.id.homepage_f1_details_text);
        unfoldTextview.setClickable(true);

        unfoldTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                unfoldTextview.scrollTo(0, 0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                unfoldtextheigh = unfoldTextview.getLineHeight() * unfoldTextview.getLineCount() - unfoldTextview.getHeight();
            }
        });


        MainActivity.listTouchInterceptor = view.findViewById(R.id.homepage_f1_touch_interceptor_view);
        MainActivity.listTouchInterceptor.setClickable(false);

        MainActivity.detailsLayout = view.findViewById(R.id.homepage_f1_details_layout);
        MainActivity.detailsLayout.setVisibility(View.INVISIBLE);

        MainActivity.unfoldableView = view.findViewById(R.id.homepage_f1_unfoldable_view);
        MainActivity.unfoldableView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (finishload) {
                    int y = (int) event.getY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                         // yDown = y;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            yMove = y;
                            if (unfoldtext_positonY_min < yDown && unfoldtext_positonY_max > yDown) {
                                if ((yMove - yDown) < 0) {
                                    //向上滑动，文本向上滚动，Y增加
                                    i = yDown-yMove;
                                    if (unfoldTextview.getScrollY() + i +100>unfoldtextheigh)
                                        //快要到头了，不能滚出去
                                            unfoldTextview.scrollTo(0, unfoldtextheigh+100);
                                    else
                                        unfoldTextview.scrollBy(0, i);
                                } else {
                                    //向下滑动，文本向下滚动Y减少
                                    i = yDown-yMove;
                                    if (unfoldTextview.getScrollY() + i < 0)
                                        //快要到头了，不能滚出去
                                        unfoldTextview.scrollTo(0, 0);
                                    else
                                        unfoldTextview.scrollBy(0, i);
                                }
                                //rootView.getParent().requestDisallowInterceptTouchEvent(false);
                                return true;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                }
                return false;
            }

        });


        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.dashuibi);
        MainActivity.unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        MainActivity.unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(true);
                MainActivity.detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(false);
                MainActivity.detailsLayout.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void inilistItem(View view) {
        /*
        *url=http://58.87.108.125:8888/reading
        *请求
        *JSON格式
        * 接收
        * {
        * "title0"="111"
        * "url0"="http://www.baidu.com"
        * "context0"="11111111111111111111111111111111111111"
        * }
         */
        //初始化表项目，不考虑刷新新增，一次性
        listView = view.findViewById(R.id.homepage_f1_list_view);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在拉取阅读列表");
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        // params.put("word", word);
        String url = requstrul;
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                JSONObject respondsjson = JSON.parseObject(str);
                int step = 0;
                List<Painting> listitem = new ArrayList<Painting>();
                while (true) {
                    String title = null;
                    String url = null;
                    String date = null;
                    String wordNumber = null;
                    String passageId = null;
                    String type = null;

                    if ((respondsjson.getString(Integer.toString(step))) != null) {
                        JSONObject jsonObject = JSONObject.parseObject(respondsjson.getString(Integer.toString(step)));
                        date = jsonObject.getString("date");
                        wordNumber = jsonObject.getString("wordNumber");
                        url = MainActivity.serverip + jsonObject.getString("imageUrl").substring(1);
                        passageId = jsonObject.getString("passageId");
                        title = jsonObject.getString("title");
                        type = jsonObject.getString("type");
                        step++;
                        Painting paintings = new Painting(url, title, wordNumber, passageId, type, date);
                        listitem.add(paintings);
                    } else {
                        break;
                    }
                }
                listView.setAdapter(new PaintingsAdapter(listitem));
                pDialog.cancel();
                finishload = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                pDialog.setTitleText("未知错误")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                finishload = false;
            }
        });

    }
}





