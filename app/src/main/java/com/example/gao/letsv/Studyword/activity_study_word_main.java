package com.example.gao.letsv.Studyword;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.MyListAdatper.MyAdapter_study_main;
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
 * Created by gangchang on 2018/5/27.
 */

public class activity_study_word_main extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private GestureDetector gd = null;
    private UnScrollListView wordinformation_listview = null;
    private TextView wordtitle = null;
    private TextView memorymethod = null;
    private ImageView horn = null;
    private MyScrollView myScrollView = null;
    private ImageView word_add = null;
    private ImageView word_collection = null;


    private List<Map<String, Object>> dataList = null;
    private JSONObject respondsjson = null;
    private String[] words_array = null;
    private String cur_word = null;
    private int cur_word_sit = 0;
    private String musicurl = null;

    private static String requstrul = MainActivity.serverip + "studyword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //获取本组单词词表
        setContentView(R.layout.study_word);
        Bundle bundle = getIntent().getExtras();
        words_array = bundle.getStringArray("words");
        cur_word_sit = 0;
        cur_word = words_array[cur_word_sit];

        //滑动手势相关
        gd = new GestureDetector((GestureDetector.OnGestureListener) this);
        //滚动布局
        myScrollView = (MyScrollView) findViewById(R.id.studay_word_MyScrollView);

        //最上方单词标题
        wordtitle = (TextView) findViewById(R.id.studay_word_title);

        //喇叭按钮
        horn = (ImageView) findViewById(R.id.study_word_horn);

        //记忆方法
        memorymethod = (TextView) findViewById(R.id.study_word_textView_TYPE_MEMORY_METHOD);

        //详细信息列表
        wordinformation_listview = (UnScrollListView) findViewById(R.id.study_word_listitem);
        wordinformation_listview.setFocusable(false);

        //添加按钮
        word_add = (ImageView) findViewById(R.id.study_word_button_add);
        word_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //收藏按钮
        word_collection = (ImageView) findViewById(R.id.study_word_button_collection);
        word_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        {
//            //根据屏幕比例设置组件高度
//            //设置scrollview的高度
//            WindowManager manager = this.getWindowManager();
//            DisplayMetrics outMetrics = new DisplayMetrics();
//            manager.getDefaultDisplay().getMetrics(outMetrics);
//            int screenHeight = outMetrics.heightPixels;
//            int screenWidth = outMetrics.widthPixels;
//            ViewGroup.LayoutParams params = myScrollView.getLayoutParams();
//            params.height = screenHeight * 9 / 10;
//            myScrollView.setLayoutParams(params);
//
//            //设置下方两个按钮的高度
//            params = word_add.getLayoutParams();
//            params.height = screenHeight / 10;
//            params.width = screenWidth / 2;
//            word_add.setLayoutParams(params);
//            params = word_collection.getLayoutParams();
//            params.height = screenHeight / 10;
//            params.width = screenWidth / 2;
//            word_collection.setLayoutParams(params);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initDataList(cur_word);//初始化数据
            }
        });

    }

    private void initDataList(String word) {
        /*
        *url=http://58.87.108.125:8888/studyword
        *请求
        *JSON格式
        * word="persist"
        * 接收
        * {
        * "WORD":"<font color='#87CEFA'>per</font><font color='#7B68EE'>sist</font>",//内容用html修饰
        * "MEMORYMETHOD":"其实我也不知道怎么记忆,balabalabala"，//考虑用html修饰
        * "MUISC":"http://www.baidu.com",
        * "OTHER":"{
        *           "title0":"基础例句",
        *           "content0":"<font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font><br><font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font>",
        *           "title1":"..."
        *           "content1":".............."
        *           }
        * }
         */
        SweetAlertDialog pDialog = new SweetAlertDialog(activity_study_word_main.this, SweetAlertDialog.PROGRESS_TYPE);
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
                memorymethod.setText(respondsjson.getString("MEMORYMETHOD"));
                musicurl = respondsjson.getString("MUSIC");
                horn.setOnClickListener(new View.OnClickListener() {
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


                if (respondsjson.getString("OTHER") != null) {
                    JSONObject jsonohter = JSON.parseObject(respondsjson.getString("OTHER"));
                    int step = 0;
                    dataList = new ArrayList<Map<String, Object>>();
                    while (true) {
                        String title = null;
                        String content = null;
                        if ((title = jsonohter.getString("title" + Integer.toString(step))) == null) {
                            break;
                        }
                        content = jsonohter.getString("content" + Integer.toString(step));
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("titleview", title);
                        map.put("explainview", content);
                        dataList.add(map);
                        step++;
                    }
                    MyAdapter_study_main adapter = new MyAdapter_study_main(getApplicationContext(), dataList, R.layout.study_word_main_listitem);
                    wordinformation_listview.setAdapter(adapter);
                    pDialog.cancel();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
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


//        wordtitle.setText(Html.fromHtml("<font color='#87CEFA'>per</font><font color='#7B68EE'>sist</font>"));
//        dataList = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("titleview", "基础例句");
//        map.put("explainview", "<font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font><br><font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font>");
//        dataList.add(map);
//        map = new HashMap<String, Object>();
//        map.put("titleview", "基础例句");
//        map.put("explainview", "<font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font><br><font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font>");
//        dataList.add(map);
//        MyAdapter_study_main adapter = new MyAdapter_study_main(this, dataList, R.layout.study_word_main_listitem);
//        wordinformation_listview.setAdapter(adapter);

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
            //向左滑,下一个
            if (cur_word_sit < words_array.length - 1) {
                cur_word_sit++;
                cur_word = words_array[cur_word_sit];
                //请求服务器，更新整个页面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initDataList(cur_word);//初始化数据
                    }
                });
            } else {
                //这组学完了，下一组,测试
                finish();
            }

        } else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 50) {
            //向右滑,上一个
            if (cur_word_sit > 0) {
                cur_word_sit--;
                cur_word = words_array[cur_word_sit];
                //请求服务器，更新整个页面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initDataList(cur_word);//初始化数据
                    }
                });
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gd.onTouchEvent(motionEvent);
    }
}
