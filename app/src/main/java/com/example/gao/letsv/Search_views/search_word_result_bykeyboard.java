package com.example.gao.letsv.Search_views;

import android.content.Intent;
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

public class search_word_result_bykeyboard extends AppCompatActivity {

    // private GestureDetector gd = null;
    private UnScrollListView wordinformation_listview = null;
    private TextView wordtitle = null;
    private TextView PHONETIC_SYMBOL = null;
    private TextView memorymethod = null;
    private TextView explain = null;
    private ImageView horn = null;
    private MyScrollView myScrollView = null;
//    private ImageView word_add = null;
//    private ImageView word_collection = null;

    private List<Map<String, Object>> dataList = null;
    private JSONObject respondsjson = null;
    // private String[] words_array = null;
    // private String cur_word = null;
    // private int cur_word_sit = 0;
    private String musicurl = null;

    private static String requstrul = MainActivity.serverip + "studyword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //获取本组单词词表
        setContentView(R.layout.search_word_result_bykeyboard);
        Bundle bundle = getIntent().getExtras();
        respondsjson = JSON.parseObject(bundle.getString("json"));

        //滑动手势相关
        //gd = new GestureDetector(this, this);
        //滚动布局
        myScrollView = (MyScrollView) findViewById(R.id.studay_word_MyScrollView);


        //最上方单词标题
        wordtitle = (TextView) findViewById(R.id.studay_word_title);

        //音标
        PHONETIC_SYMBOL = (TextView) findViewById(R.id.study_word_textView_PHONETIC_SYMBOL);

        //含义
        explain = (TextView) findViewById(R.id.study_word_textView_explain);

        //喇叭按钮
        horn = (ImageView) findViewById(R.id.study_word_horn);

        //记忆方法
        memorymethod = (TextView) findViewById(R.id.study_word_textView_TYPE_MEMORY_METHOD);

        //详细信息列表
        wordinformation_listview = (UnScrollListView) findViewById(R.id.study_word_listitem);
        wordinformation_listview.setFocusable(false);

//        //添加按钮
//        word_add = (ImageView) findViewById(R.id.study_word_button_add);
//        word_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        //收藏按钮
//        word_collection = (ImageView) findViewById(R.id.study_word_button_collection);
//        word_collection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


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
                initDataList();//初始化数据
            }
        });

    }

    private void initDataList() {
        wordtitle.setText(Html.fromHtml(respondsjson.getString("word")));
        PHONETIC_SYMBOL.setText(respondsjson.getString("ps"));
        explain.setText(respondsjson.getString("meaning"));
        memorymethod.setText(respondsjson.getString("memory"));
        musicurl = respondsjson.getString("music");
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
        int step = 0;
        dataList = new ArrayList<Map<String, Object>>();
        while (true) {
            String title = null;
            String content = null;
            if ((title = respondsjson.getString("title" + Integer.toString(step))) == null) {
                break;
            }
            content = respondsjson.getString("content" + Integer.toString(step));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titleview", title);
            map.put("explainview", content);
            dataList.add(map);
            step++;
        }
        MyAdapter_study_main adapter = new MyAdapter_study_main(getApplicationContext(), dataList, R.layout.study_word_main_listitem);
        wordinformation_listview.setAdapter(adapter);

    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        gd.onTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//
//    }
//@Override
//public boolean onTouch(View view, MotionEvent motionEvent) {
//    return gd.onTouchEvent(motionEvent);
//}


//e1  The first down motion event that started the fling.手势起点的移动事件
//e2  The move motion event that triggered the current onFling.当前手势点的移动事件
//velocityX   The velocity of this fling measured in pixels per second along the x axis.每秒x轴方向移动的像素
//velocityY   The velocity of this fling measured in pixels per second along the y axis.每秒y轴方向移动的像素


//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        if (e1.getX() - e2.getX() > 200 && Math.abs(velocityX) > 50) {
//            //向左滑,下一个
//            if (cur_word_sit < words_array.length - 1) {
//                cur_word_sit++;
//                cur_word = words_array[cur_word_sit];
//                //请求服务器，更新整个页面
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        myScrollView.scrollTo(0,0);
//                        initDataList(cur_word);//初始化数据
//                    }
//                });
//            } else {
//                //这组学完了，下一组,测试
//                Intent intent = new Intent(activity_study_word_main.this, activity_study_word_test.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putInt("type", 1);
//                mBundle.putStringArray("words",words_array);
//                intent.putExtras(mBundle);
//                startActivity(intent);
//                finish();
//            }
//
//        } else if (e2.getX() - e1.getX() > 200 && Math.abs(velocityX) > 50) {
//            //向右滑,上一个
//            if (cur_word_sit > 0) {
//                cur_word_sit--;
//                cur_word = words_array[cur_word_sit];
//                //请求服务器，更新整个页面
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        initDataList(cur_word);//初始化数据
//                    }
//                });
//            }
//        }
//        return false;
//    }


}
