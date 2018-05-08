package com.example.gao.letsv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PhraseActivity extends AppCompatActivity  implements  android.view.GestureDetector.OnGestureListener{
    GestureDetector detector;
    Bundle bundle;
    TextView phrase1,phrase2,phrase3,phrase4,phrase5;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrasestudy);
        bundle=getIntent().getExtras();
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
        detector = new GestureDetector(this,this);

    }
    public boolean onTouchEvent(MotionEvent me){
        return detector.onTouchEvent(me);
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
        float minMove = 10;         //最小滑动距离
        float minVelocity = 0;      //最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if(beginX-endX>minMove&&Math.abs(velocityX)>minVelocity){   //左滑
            Toast.makeText(this,velocityX+"左滑",Toast.LENGTH_SHORT).show();
        }else if(endX-beginX>minMove&&Math.abs(velocityX)>minVelocity){   //右滑
            Intent intent=new Intent(PhraseActivity.this,DetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(beginY-endY>minMove&&Math.abs(velocityY)>minVelocity){   //上滑
            Toast.makeText(this,velocityX+"上滑",Toast.LENGTH_SHORT).show();
        }else if(endY-beginY>minMove&&Math.abs(velocityY)>minVelocity){   //下滑
            Toast.makeText(this,velocityX+"下滑",Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
