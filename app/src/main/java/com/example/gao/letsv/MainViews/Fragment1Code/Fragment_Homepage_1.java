


package com.example.gao.letsv.MainViews.Fragment1Code;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class Fragment_Homepage_1 extends Fragment {

    private  ListView listView;
    private static String requstrul = MainActivity.serverip + "reading";

    public Fragment_Homepage_1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_1, container, false);
        initView(view);
        inilistItem(view);
        return view;
    }

    private void initView(View view) {
        MainActivity.listTouchInterceptor = view.findViewById(R.id.homepage_f1_touch_interceptor_view);
        MainActivity.listTouchInterceptor.setClickable(false);

        MainActivity.detailsLayout = view.findViewById(R.id.homepage_f1_details_layout);
        MainActivity.detailsLayout.setVisibility(View.INVISIBLE);

        MainActivity.unfoldableView = view.findViewById(R.id.homepage_f1_unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.fill_blank);
        MainActivity.unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        MainActivity.unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                MainActivity. listTouchInterceptor.setClickable(true);
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
                MainActivity. listTouchInterceptor.setClickable(false);
                MainActivity. detailsLayout.setVisibility(View.INVISIBLE);
            }
        });

    }



    private  void inilistItem(View view){
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
        pDialog.setTitleText("Loading");
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
                int step=0;
                List<Painting> listitem=new ArrayList<Painting>();
                while(true){
                    String title=null;
                    String url=null;
                    String context=null;
                    if((title=respondsjson.getString("title"+Integer.toString(step)))!=null){
                        url=respondsjson.getString("url"+Integer.toString(step));
                        context=respondsjson.getString("context"+Integer.toString(step));
                        step++;
                        Painting paintings = new Painting(url,title,context);
                        listitem.add(paintings);
                    }else{
                        break;
                    }
                }
                listView.setAdapter(new PaintingsAdapter(listitem));
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
}





