package com.example.gao.letsv;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dd.processbutton.iml.SubmitProcessButton;
import com.loopj.android.http.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


/**
 * Created by gangchang on 2018/4/10.
 */

public class LoginActivity extends AppCompatActivity {
    private LoginVideoView videoview;
    SubmitProcessButton btnlogin = null;
    TextView btnres = null;
    TextView zhaohuimima = null;
    EditText zhanghao = null;
    EditText mima = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();

    }

    private void initView() {
        btnlogin = (SubmitProcessButton) findViewById(R.id.login_btnSignIn);
        btnres = (TextView) findViewById(R.id.login_zhuce);
        zhanghao = (EditText) findViewById(R.id.login_zhanghao);
        mima = (EditText) findViewById(R.id.login_password);
        zhaohuimima = (TextView) findViewById(R.id.login_wangjimima);


        zhaohuimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, activity_forgetpassword_inputphone.class);
                LoginActivity.this.startActivity(mainIntent);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (zhanghao.getText().toString().length() < 6 || zhanghao.getText().toString().length() > 18) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确的账号");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else if (mima.getText().toString().length() < 6 || mima.getText().toString().length() > 18) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确的密码");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    //封装需要传递的参数
                    RequestParams params = new RequestParams();
                    params.put("username", zhanghao.getText().toString());
                    params.put("password", mima.getText().toString());
                    String url = "http://58.87.108.125:8080/login";
                    client.post(url, params, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String str = new String(responseBody);
                            JSONObject jsonObject = JSONObject.parseObject(str);
                            int state = jsonObject.getInteger("state");
                            if (state == 0) {
                                //登录成功
                                try {
                                    File file = new File(getCacheDir().getPath(), "config.properties");
                                    if (!file.exists())
                                        file.createNewFile();
                                    FileOutputStream fo = new FileOutputStream(file);
                                    Properties properties = new Properties();
                                    properties.put("username", zhanghao.getText().toString().trim());
                                    properties.put("password", mima.getText().toString());
                                    properties.store(fo, "");
                                    fo.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                MainActivity.password=mima.getText().toString();
                                MainActivity.username=zhanghao.getText().toString();
                                MainActivity.autologin=true;
                                pDialog.setTitleText("登录成功")
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                            } else {
                                pDialog.setTitleText("密码错误")
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            pDialog.setTitleText("登录失败")
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                        }
                    });
                }
            }
        });
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(mainIntent);

            }
        });
        videoview = (LoginVideoView) findViewById(R.id.login_videoview);
        //circularProgressButton=(CircularProgressButton) findViewById(R.id.btnWithText) ;
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logvideo));
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f,0f);
                videoview.start();
            }
        });

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


}
