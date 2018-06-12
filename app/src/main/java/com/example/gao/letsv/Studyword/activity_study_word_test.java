package com.example.gao.letsv.Studyword;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.MyListAdatper.MyAdapter_study_test;
import com.example.gao.letsv.MyWidget.UnScrollListView;
import com.example.gao.letsv.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * Created by gangchang on 2018/6/5.
 */

public class activity_study_word_test extends AppCompatActivity{
    private TextView wordtitle = null;
    private ImageView wordhorn = null;
    private UnScrollListView word_selectmeans_listview = null;
    private ImageView unkonowword = null;
    private ImageView knowword = null;

    private List<Map<String, Object>> dataList = null;
    private JSONObject respondsjson = null;
    private String[] words_array = null;
    private String cur_word = null;
    private int cur_word_sit = 0;
    private String musicurl = null;

    private static String requstrul = MainActivity.serverip+"testword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取本组单词词表
        setContentView(R.layout.study_word_test);
        Bundle bundle = getIntent().getExtras();
        words_array = bundle.getStringArray("words");
        cur_word_sit = 0;
        cur_word = words_array[cur_word_sit];

        //单词标题
        wordtitle = (TextView) findViewById(R.id.study_word_test_title);

        //喇叭
        wordhorn = (ImageView) findViewById(R.id.study_word_test_horn);

        //待选列表
        word_selectmeans_listview = (UnScrollListView) findViewById(R.id.study_word_test_listitem);

        //不认识按钮
        unkonowword = (ImageView) findViewById(R.id.study_word_test_button_unknow);

        //认识按钮
        knowword = (ImageView) findViewById(R.id.study_word_test_button_know);

        //刷新界面
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initDataList(cur_word);
            }
        });

    }


    private void initDataList(String word) {
        /*
        *url=http://58.87.108.125:8888/testword
        *请求
        *JSON格式
        * word="insistence"
        * 接收
        * {
        * "WORD":"persist,//不用html修饰
        * "MUISC":"http://www.baidu.com",
        * "OTHER":"{
        *           "0":"n.强调",
        *           "1":"v.强势",
        *           "2":"n.坚持",
        *           "3":"n.实例",
        *           ....
        *           "right":"2"
        *           }
        * }
         */
        SweetAlertDialog pDialog = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("word", word);
        String url = requstrul;
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                respondsjson = JSON.parseObject(str);
                wordtitle.setText(Html.fromHtml(respondsjson.getString("WORD")));
                musicurl = respondsjson.getString("MUSIC");
                wordhorn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //播放音乐
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                        try {
                            mediaPlayer.setDataSource(musicurl);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


                JSONObject jsonohter = JSON.parseObject(respondsjson.getString("OTHER"));
                int step = 0;
                dataList = new ArrayList<Map<String, Object>>();
                while (true) {
                    String title = null;
                    if ((title = jsonohter.getString(Integer.toString(step))) == null) {
                        break;
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("selecttext", title);
                    map.put("right", (Integer.parseInt(jsonohter.getString("right")) == step) ? "0" : "1");
                    dataList.add(map);
                    step++;
                }
                MyAdapter_study_test adapter = new MyAdapter_study_test(getApplicationContext(), dataList, R.layout.study_word_test_listitem);
                word_selectmeans_listview.setAdapter(adapter);
                word_selectmeans_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String t = adapterView.getItemAtPosition(i).toString();
                        if(t.charAt(0)=='0'){
                            //正确
                        }else{
                            //错误
                        }
                        if(cur_word_sit<words_array.length-1) {
                            cur_word_sit++;
                            cur_word=words_array[cur_word_sit];
                            initDataList(cur_word);
                        }else{
                            //开启下一组
                            finish();
                        }
                    }
                });
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

    public void select_correct() {

    }
}
