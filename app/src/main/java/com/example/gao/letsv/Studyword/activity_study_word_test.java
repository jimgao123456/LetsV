package com.example.gao.letsv.Studyword;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.MyListAdatper.MyAdapter_study_test;
import com.example.gao.letsv.MyWidget.MyScrollView;
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

public class activity_study_word_test extends AppCompatActivity {
    private MyScrollView myScrollView = null;
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

    private static String requstrul = MainActivity.serverip + "testword";
    private static int activitytype = 0;//0普通测试，1早测.2//总测

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取本组单词词表
        setContentView(R.layout.study_word_test);
        Bundle bundle = getIntent().getExtras();
        words_array = bundle.getStringArray("words");
        activitytype = bundle.getInt("type");
        cur_word_sit = 0;
        cur_word = words_array[cur_word_sit];

        //滚动布局
        myScrollView = (MyScrollView) findViewById(R.id.studay_word_test_MyScrollView);
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
        pDialog.setTitleText("拉取测试列表");
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
                wordtitle.setText(Html.fromHtml(respondsjson.getString("word")));
                musicurl = respondsjson.getString("music");
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


                int step = 0;
                dataList = new ArrayList<Map<String, Object>>();
                while (true) {
                    String title = null;
                    if ((title = respondsjson.getString(Integer.toString(step))) == null) {
                        break;
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("selecttext", title);
                    map.put("correct", (Integer.parseInt(respondsjson.getString("correct")) == step) ? "0" : "1");
                    dataList.add(map);
                    step++;
                }
                MyAdapter_study_test adapter = new MyAdapter_study_test(getApplicationContext(), dataList, R.layout.study_word_test_listitem);
                word_selectmeans_listview.setAdapter(adapter);
                word_selectmeans_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String t = adapterView.getItemAtPosition(i).toString();
                        if (t.charAt(0) == '0') {
                            //正确
                            SweetAlertDialog pDialog_right = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.SUCCESS_TYPE);
                            pDialog_right.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog_right.setTitleText("正确");
                            pDialog_right.setCancelable(false);
                            pDialog_right.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    pDialog_right.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog_right.setTitleText("正在提交选择信息");
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    RequestParams params = new RequestParams();
                                    params.put("username", MainActivity.username);
                                    params.put("word", cur_word);
                                    String url = MainActivity.serverip + "/correctword";
                                    client.post(url, params, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            pDialog_right.cancel();
                                            if (cur_word_sit < words_array.length - 1) {
                                                //下一个词
                                                changeCurWord();
                                            } else {
                                                //开启下一组
                                                updategroup();
                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers,
                                                              byte[] responseBody, Throwable error) {
                                            pDialog_right.cancel();
                                            if (cur_word_sit < words_array.length - 1) {
                                                //下一个词
                                                changeCurWord();
                                            } else {
                                                //开启下一组
                                                updategroup();
                                            }
                                        }
                                    });

                                }
                            });
                            pDialog_right.show();
                        } else {
                            //错误
                            SweetAlertDialog pDialog_error = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog_error.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog_error.setTitleText("错误");
                            pDialog_error.setCancelable(false);
                            pDialog_error.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    pDialog_error.cancel();
                                    if (cur_word_sit < words_array.length - 1) {
                                        //下一个词
                                        changeCurWord();
                                    } else {
                                        //开启下一组
                                        updategroup();
                                    }
                                }
                            });
                            pDialog_error.show();
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

    private void changeCurWord(){
        cur_word_sit++;
        cur_word = words_array[cur_word_sit];
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myScrollView.scrollTo(0, 0);
                initDataList(cur_word);//初始化数据
            }
        });
    }
    private void updategroup(){
        SweetAlertDialog pDialog_finishtest = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog_finishtest.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog_finishtest.setTitleText("正在提交信息");
        pDialog_finishtest.setCancelable(false);
        pDialog_finishtest.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", MainActivity.username);
        String url = MainActivity.serverip + "/updategroup";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog_finishtest.cancel();
                start_newgroup();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                pDialog_finishtest.cancel();
                start_newgroup();
            }
        });
    }

    private void start_newgroup() {
        if(activitytype==2){
            SweetAlertDialog pDialog = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.SUCCESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("恭喜！完成今日测试");
            pDialog.setCancelable(false);
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            });
            pDialog.show();

        }else if (Integer.parseInt(MainActivity.num) == 3) {
            SweetAlertDialog pDialog = new SweetAlertDialog(activity_study_word_test.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("正在拉取今日总测列表");
            pDialog.setCancelable(false);
            pDialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
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
                    if (words != null) {
                        //三组完成，总测
                        Intent intent = new Intent(activity_study_word_test.this, activity_study_word_test.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("type", 2);
                        mBundle.putStringArray("words", words.split(" "));
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable error) {
                    pDialog.setTitleText("未知错误")
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    finish();
                }
            });


        } else {
            //正常进入下一组
            Intent intent = new Intent(activity_study_word_test.this, activity_study_word_grouplist.class);
            startActivity(intent);
            finish();
        }
    }

}
