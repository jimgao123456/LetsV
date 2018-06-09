package com.example.gao.letsv.Studyword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * Created by gangchang on 2018/6/8.
 */

public class activity_study_word_grouplist extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private GestureDetector gd = null;
    private TextView titleview = null;
    private TextView wordlist = null;
    private TextView memoryview = null;

    private static String requstrul = "http://58.87.108.125:8888/wordgrouplist";
    private JSONObject respondsjson = null;
    private String[] words_array = null;

    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
            "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    @Override
    protected void onCreate(Bundle saveinbundle) {
        super.onCreate(saveinbundle);

        //滑动手势相关
        gd = new GestureDetector((GestureDetector.OnGestureListener) this);

        titleview = (TextView) findViewById(R.id.study_word_grouplist_grouptitle);

        wordlist = (TextView) findViewById(R.id.study_word_grouplist_groupwords);

        memoryview = (TextView) findViewById(R.id.study_word_grouplist_Memory);
    }

    private void iniData() {
        MainActivity.num = Integer.toString(Integer.parseInt(MainActivity.num) + 1);
        titleview.setText("第" + foematInteger(Integer.parseInt(MainActivity.num)) + "组");
         /*
        *url=http://58.87.108.125:8888/wordgrouplist
        *请求
        *JSON格式
        *
        * 接收
        * {
        * "WORDS":"word1 \r\n  wor2 \r\n  word3 \r\n",//换行
        * "MEMORYMETHOD":"其实我也不知道怎么记忆,balabalabala"，
         */
        SweetAlertDialog pDialog = new SweetAlertDialog(activity_study_word_grouplist.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //params.put("word", word);
        String url = requstrul;
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                respondsjson = JSON.parseObject(str);
                wordlist.setText(respondsjson.getString("WORDS"));
                memoryview.setText(respondsjson.getString("MEMORYMETHOD"));
                words_array = respondsjson.getString("WORDS").split("\r\n");
                pDialog.cancel();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.setTitleText("未知错误")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                });
            }
        });

    }

    private static String foematInteger(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        System.out.println("----" + len);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    //当前val[i]的下一个值val[i-1]为0则不输出零
                    continue;
                } else {
                    //只有当当前val[i]的下一个值val[i-1]不为0才输出零
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    //e1  The first down motion event that started the fling.手势起点的移动事件
//e2  The move motion event that triggered the current onFling.当前手势点的移动事件
//velocityX   The velocity of this fling measured in pixels per second along the x axis.每秒x轴方向移动的像素
//velocityY   The velocity of this fling measured in pixels per second along the y axis.每秒y轴方向移动的像素
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 50) {
            //向左滑,开始学习
            if (words_array != null) {
                Intent intent = new Intent(this, activity_study_word_main.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("words", words_array);
                startActivity(intent);
                finish();
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gd.onTouchEvent(motionEvent);
    }
}
