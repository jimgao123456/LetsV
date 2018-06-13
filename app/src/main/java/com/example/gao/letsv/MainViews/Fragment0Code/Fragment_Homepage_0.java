package com.example.gao.letsv.MainViews.Fragment0Code;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    private static String requstrul = MainActivity.serverip + "changeplan";
    ImageView btn_startstudy= null;
    Button changeplane=null;
    TextView planetext=null;
    public Fragment_Homepage_0() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_0, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        planetext=(TextView)view.findViewById(R.id.homepage_f0_studyplane) ;

        ImageView btn_startstudy= (ImageView)view.findViewById(R.id.homepage_f0_button_startstudy);
        btn_startstudy.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {
//              Intent intent = new Intent(getActivity(), Ps.class);
//                    startActivity(intent);
              if(MainActivity.haschecklogin==true) {
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

                      Intent intent = new Intent(getActivity(), activity_study_word_grouplist.class);
                      startActivity(intent);

              }else{
                  Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
                  startActivity(mainIntent);
              }
           }
       });

        Button changeplane=(Button)view.findViewById(R.id.homepage_f0_changeplane);
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
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        final String tx=options1Items.get(options1);
                        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        params.put("username", MainActivity.username);
                        params.put("num", Integer.toString(options1));
                        String url = requstrul;
                        client.post(url, params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                planetext.setText("学习计划:"+options1Items.get(options1));
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
