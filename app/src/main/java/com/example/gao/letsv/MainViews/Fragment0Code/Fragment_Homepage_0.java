package com.example.gao.letsv.MainViews.Fragment0Code;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.gao.letsv.LoginViews.LoginActivity;
import com.example.gao.letsv.MainViews.Fragment1Code.Painting;
import com.example.gao.letsv.MainViews.Fragment1Code.PaintingsAdapter;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.Studyword.activity_study_word_test;
import com.example.gao.letsv.R;
import com.example.gao.letsv.Studyword.activity_study_word_grouplist;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_0 extends Fragment {

    private View rootView;
    private static boolean finishload = false;
    private static String requstrul = MainActivity.serverip + "changeplan";
    ImageView btn_startstudy = null;
   Button changeplane = null;
    public static  TextView planetext = null;

    public Fragment_Homepage_0() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == rootView) {
            View view = inflater.inflate(R.layout.fragment_homepage_0, null);
            rootView = view;
            if (getUserVisibleHint()) {
                initView(view);
                finishload = true;
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
                    finishload = true;

            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        planetext = (TextView) view.findViewById(R.id.homepage_f0_studyplane);
        if (MainActivity.state_login) {
            switch (MainActivity.nowplane) {
                case 0:
                    planetext.setText("学习计划:四级");
                    break;
                case 1:
                    planetext.setText("学习计划:六级");
                    break;
                case 2:
                    planetext.setText("学习计划:雅思/托福");
                    break;
            }

        }


        ImageView btn_startstudy = (ImageView) view.findViewById(R.id.homepage_f0_button_startstudy);
        btn_startstudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Intent intent = new Intent(getActivity(), Ps.class);
//                    startActivity(intent);
                if (MainActivity.state_login == true) {
//                  String[] group1 = {"include", "conclude", "seclude", "exclude", "preclude", "occlude"};
//                  String[] group2 = {"attain", "obtain", "retain", "sustain", "maintain"};
//                  String[] group3 = {"resist", "consist", "subsist", "persist", "desist"};
//                  int nGroup = 1;
//                  int nNum = 1;
//                  Bundle mBundle = new Bundle();
//                  mBundle.putStringArray("group1", group1);
//                  mBundle.putStringArray("group2", group2);
//                  mBundle.putStringArray("group3", group3);
//                  mBundle.putInt("ngroup", nGroup);
//                  mBundle.putInt("nnum", nNum);
//                  Intent mainIntent = new Intent(getActivity(), PhraseActivity.class);
//                  mainIntent.putExtras(mBundle);
//                  startActivity(mainIntent);
                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("正在拉取今日早测列表");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    AsyncHttpClient client = new AsyncHttpClient(8888);
                    RequestParams params = new RequestParams();
                    params.put("username", MainActivity.username);
                    String url = MainActivity.serverip + "/gettestlist";
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            pDialog.cancel();
                            String str = new String(responseBody);
                            JSONObject jsonObject = JSONObject.parseObject(str);
                            String words = jsonObject.getString("list");
                            if (words.equals("")) {
                                //没早测
                                Intent intent = new Intent(getActivity(), activity_study_word_grouplist.class);
                                startActivity(intent);
                            } else {
                                //早测
                                Intent intent = new Intent(getActivity(), activity_study_word_test.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putInt("type", 0);
                                mBundle.putStringArray("words", words.split(" "));
                                intent.putExtras(mBundle);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] responseBody, Throwable error) {
                            pDialog.setTitleText("未知错误")
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    });


                } else {
                    Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(mainIntent);
                }
            }
        });

        Button changeplane = (Button) view.findViewById(R.id.homepage_f0_changeplane);
        changeplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(MainActivity.username==null)
//                    return;
                final ArrayList<String> options1Items = new ArrayList<>();
                options1Items.add("四级");
                options1Items.add("六级");
                options1Items.add("雅思/托福");
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        if (options1 == MainActivity.nowplane)
                            return;
                        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("正在提交更改");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        AsyncHttpClient client = new AsyncHttpClient(8888);
                        RequestParams params = new RequestParams();
                        params.put("username", MainActivity.username);
                        params.put("level", Integer.toString(options1));
                        String url = requstrul;
                        client.post(url, params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                planetext.setText("学习计划:" + options1Items.get(options1));
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
                }).setCancelText("取消").setSubmitText("确定").build();
                pvOptions.setPicker(options1Items);
                pvOptions.show();
            }
        });
    }
}
