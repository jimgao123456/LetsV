package com.example.gao.letsv.MainViews.Fragment2Code;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.gao.letsv.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xjl on 2018/6/5.
 */

public class activity_media_player extends Activity {
    private Button btnPause, btnPlayUrl, btnStop;
    private SeekBar skbProgress;
    private Player player;
    private String title;
    private String url;
    private static boolean playerstate = false;
    private CircleImageView circleImageView;


    //旋转图片需要的
    private  ValueAnimator oa;
    private  float rotation=0;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.onDestroy();
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midia_player);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        //url="http://ww.danmu.fm:233/いけないボーダーライン.m4a";
        this.setTitle(title);

        circleImageView = (CircleImageView) this.findViewById(R.id.media_player_circleimg);

        btnPlayUrl = (Button) this.findViewById(R.id.media_player_btnPlayUrl);
        btnPlayUrl.setOnClickListener(new ClickEvent());

        btnPause = (Button) this.findViewById(R.id.media_player_btnPause);
        btnPause.setOnClickListener(new ClickEvent());

        btnStop = (Button) this.findViewById(R.id.media_player_btnStop);
        btnStop.setOnClickListener(new ClickEvent());

        skbProgress = (SeekBar) this.findViewById(R.id.media_player_skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        player = new Player(skbProgress);
        player.playUrl(url);

        playerstate = true;
        oa=ObjectAnimator.ofFloat(circleImageView,"rotation",0,360).setDuration(5000);
        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotation=(float)oa.getAnimatedValue();
                circleImageView.setRotation(rotation);
            }
        });
        oa.setStartDelay(0);
        oa.setInterpolator(new LinearInterpolator());
        oa.setRepeatCount(ValueAnimator.INFINITE);
        oa.start();


    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            if (arg0 == btnPause) {
                playerstate = false;
                oa.pause();
                player.pause();
            } else if (arg0 == btnPlayUrl) {
                //在百度MP3里随便搜索到的,大家可以试试别的链接
                //String url="http://219.138.125.22/myweb/mp3/CMP3/JH19.MP3";
                playerstate = true;
                oa.resume();
                player.play();
            } else if (arg0 == btnStop) {
                playerstate = false;
                oa.pause();
                player.stop();

            }
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }


}
