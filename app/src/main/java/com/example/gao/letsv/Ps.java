package com.example.gao.letsv;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.MainViews.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gangchang on 2018/5/22.
 */

public class Ps extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    ImageView imageView = null;
    GestureDetector gd = null;
    ConstraintLayout constraintLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ps);
        imageView = (ImageView) findViewById(R.id.imageView5);
        Class drawable  =  R.drawable.class;
            imageView.setImageResource( getResources().getIdentifier("l"+ MainActivity.num,
                "drawable", getClass().getPackage().getName()));
        gd = new GestureDetector((GestureDetector.OnGestureListener) this);
        constraintLayout = (ConstraintLayout) findViewById(R.id.pslayout1);
        constraintLayout.setOnTouchListener(this);
        constraintLayout.setLongClickable(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("number", Integer.parseInt(MainActivity.num)-1);
        String url = "http://58.87.108.125:8888/audio";
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                JSONObject jsonObject = JSONObject.parseObject(str);
                String url= jsonObject.getString("url");
                if(!url.startsWith("n")) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                    try {
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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
            if(Integer.parseInt(MainActivity.num)==48){
                MainActivity.num="49";
                finish();
            }else if(Integer.parseInt(MainActivity.num)==59){
                MainActivity.num="0";
                finish();
            }else {
                MainActivity.num = Integer.toString(Integer.parseInt(MainActivity.num) + 1);
                Intent intent = new Intent(Ps.this, Ps.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
                finish();
            }
        }
//        } else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 50) {
//            if (Integer.parseInt(num) == 0)
//                return false;
//            Bundle mBundle = new Bundle();
//            mBundle.putString("num", Integer.toString(Integer.parseInt(num) - 1));
//            Intent intent = new Intent(Ps.this, Ps.class);
//            intent.putExtras(mBundle);
//            startActivity(intent);
//        }

        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gd.onTouchEvent(motionEvent);
    }

}
