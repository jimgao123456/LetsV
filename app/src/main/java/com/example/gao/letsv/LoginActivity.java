package com.example.gao.letsv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dd.processbutton.iml.SubmitProcessButton;


/**
 * Created by gangchang on 2018/4/10.
 */

public class LoginActivity extends AppCompatActivity {
    private LoginVideoView videoview;
    SubmitProcessButton btnlogin = null;
    SubmitProcessButton btnres=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();

    }

    private void initView() {
        btnlogin = (SubmitProcessButton) findViewById(R.id.btnSignIn);
        btnres=(SubmitProcessButton)findViewById(R.id.test1) ;
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (btnlogin.getProgress() == 0)
//                    btnlogin.setProgress(50);
//                else if (btnlogin.getProgress() == 50)
//                    btnlogin.setProgress(100);
//                else
//                    btnlogin.setProgress(0);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                  int i=0;
                  while(i<101){
                      btnlogin.setProgress(i);
                      try {
                          Thread.sleep(100);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      i++;
                  }
                    }
                },0);
            }
        });
btnres.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(mainIntent);
     
    }
});
        videoview = (LoginVideoView) findViewById(R.id.videoview);
        //circularProgressButton=(CircularProgressButton) findViewById(R.id.btnWithText) ;
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logvideo));
        videoview.start();
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
        videoview.stopPlayback();
    }


    //登录点击事件
    void Button_login() {


    }
}
