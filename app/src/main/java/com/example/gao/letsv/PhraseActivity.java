package com.example.gao.letsv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;


public class PhraseActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    TextView phrase1,phrase2,phrase3,phrase4,phrase5;
    private GestureDetector gestureDetector;                    //手势检测
    private GestureDetector.OnGestureListener onSlideGestureListener = null;    //左右滑动手势检测监听器
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasestudy);
        Bundle bundle =getIntent().getExtras();
        int nNum=bundle.getInt("nnum");
        int nGroup=bundle.getInt("ngroup");
        String[] group=bundle.getStringArray("group"+nGroup);
        if(group==null) group=new String[5];
        phrase1=findViewById(R.id.phrase1);
        phrase2=findViewById(R.id.phrase2);
        phrase3=findViewById(R.id.phrase3);
        phrase4=findViewById(R.id.phrase4);
        phrase5=findViewById(R.id.phrase5);
        phrase1.setText(group[0]);
        phrase2.setText(group[1]);
        phrase3.setText(group[2]);
        phrase4.setText(group[3]);
        phrase5.setText(group[4]);
        gestureDetector = new GestureDetector(this, this);


    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 参数解释：
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        // 触发条件 ：
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
        if ((e1 == null) || (e2 == null)){
            return false;
        }
        int FLING_MIN_DISTANCE = 100;
        int FLING_MIN_VELOCITY = 100;
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY)
        {
            // 向左滑动
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
//此处也可以加入对滑动速度的要求
//                       && Math.abs(velocityX) > FLING_MIN_VELOCITY
                )
        {
            // 向右滑动
            Intent intent = new Intent();
            intent.setClass(PhraseActivity.this, DetailActivity.class);
//              intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //不重复打开多个界面
            startActivity(intent);
            overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        }
        return false;
    }
}
