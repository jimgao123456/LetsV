package com.example.gao.letsv;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dd.CircularProgressButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mob.MobSDK;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.sql.Time;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {
    private EditText siname = null;
    private EditText sipassword = null;
    private EditText sitele = null;
    private EditText checknum = null;
    private EditText sipassword2 = null;
    private TextView nameTips = null;
    private TextView passwordTips = null;
    private TextView teleTips = null;
    private TextView mailTips = null;
    private TextView passwordTips2 = null;
    private CustomVideoView videoview;
    private Button checkBtn = null;
    private TimeCount time;
    public int smsFlage = 0;//0:设置为初始化值 1：请求获取验证码 2：提交用户输入的验证码判断是否正确
    public SweetAlertDialog pDialog=null;
    private EventHandler eh;
    //TODO:判断是否位邮箱
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //TODO:判断用户名合法性
    public boolean isusername(String user) {
        String str = "^[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(user);
        return m.matches();
    }

    Button finisires = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
    @Override
    protected void onStop() {
        super.onStop();
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.siname = (EditText) super.findViewById(R.id.register_signname);
        this.sipassword = (EditText) super.findViewById(R.id.register_sign_password);
        this.sipassword2 = (EditText) super.findViewById(R.id.register_sign_password2);
        this.sitele = (EditText) super.findViewById(R.id.register_sign_tele);
        this.checknum = (EditText) super.findViewById(R.id.register_checknumber);
        this.nameTips = (TextView) super.findViewById(R.id.register_nametip);
        this.passwordTips = (TextView) super.findViewById(R.id.register_passwordtip);
        this.passwordTips2 = (TextView) super.findViewById(R.id.register_passwordtip2);
        this.teleTips = (TextView) super.findViewById(R.id.register_teletip);
        this.checkBtn = (Button) super.findViewById(R.id.register_check_button);
        this.time = new TimeCount(60000, 1000);
        sipassword.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (sipassword.getText().toString().length() < 6) {
                    passwordTips.setText("密码不得小于6位");
                } else if (sipassword.getText().toString().length() > 18) {
                    passwordTips.setText("密码不得大于18位");
                } else if (sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                    passwordTips2.setVisibility(View.INVISIBLE);
                } else {
                    passwordTips.setText(" ");
                }
                return false;
            }
        });
        //TODO;添加密码焦点框监听
        sipassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    passwordTips.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    passwordTips.setVisibility(View.INVISIBLE);
                }
            }
        });

        //TODO;添加密码确认焦点框监听
        sipassword2.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if (sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                        passwordTips2.setVisibility(View.INVISIBLE);
                    } else {
                        passwordTips2.setVisibility(View.VISIBLE);
                    }
                } else {
                    // 此处为失去焦点时的处理内容
                    passwordTips2.setVisibility(View.INVISIBLE);
                }
            }
        });
        //TODO;添加密码确认输入框监听
        sipassword2.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                    passwordTips2.setVisibility(View.INVISIBLE);
                } else {
                    passwordTips2.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        //TODO:添加用户名输入框监听
        siname.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (siname.getText().toString().length() < 6) {
                    nameTips.setText("用户名不得小于六位");
                } else if (siname.getText().toString().length() > 18) {
                    nameTips.setText("用户名不得大于18位");
                } else if (!isusername(siname.getText().toString())) {
                    nameTips.setText("用户名只能由数字和英文字母组成");
                } else {
                    nameTips.setText(" ");
                }
                return false;
            }
        });
        //TODO：添加用户名焦点监听
        siname.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    nameTips.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    nameTips.setVisibility(View.INVISIBLE);
                }
            }
        });
        sitele.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (sitele.getText().toString().length() != 11) {
                    teleTips.setText("请输入正确的手机号");
                    teleTips.setVisibility(View.VISIBLE);
                } else {
                    teleTips.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });


        final QMUIRoundButton circularButton1 = (QMUIRoundButton) findViewById(R.id.register_btn_register);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断输入内容
                if (siname.getText().toString().length() < 6 || siname.getText().toString().length() > 18 || !isusername(siname.getText().toString())) {
                    if(pDialog==null) {
                        pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("用户名须为6-18的数字及字母组合");
                        pDialog.setCancelable(false);
                        pDialog.show();
                    }else{
                        pDialog.setTitleText("未知错误")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                } else if (sipassword.getText().toString().length() < 6 || sipassword.getText().toString().length() > 18) {
                    pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("密码须为6-18的数字及字母组合");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else if (!sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                     pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("两次密码输入不一致");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else if (sitele.getText().toString().length() != 11) {
                     pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确手机号");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else if (checknum.getText().length() != 4) {
                    pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确验证码");
                    pDialog.setCancelable(false);
                    pDialog.show();
                } else {
                    pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    // 检测验证码
                    smsFlage=2;
                  SMSSDK.submitVerificationCode("86", sitele.getText().toString(), checknum.getText().toString());

                }

            }
        });

        MobSDK.init(this);
        eh=new EventHandler() {
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提交验证码成功
                                //注册
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("username", sitele.getText().toString());
                                params.put("password", sipassword.getText().toString());
                                params.put("nickname",siname.getText().toString());
                                String url = "http://58.87.108.125:8888/register";

                                client.post(url, params, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String str = new String(responseBody);
                                        JSONObject jsonObject = JSONObject.parseObject(str);
                                        int state = jsonObject.getInteger("state");
                                        if (state == 0) {
                                            pDialog.setTitleText("注册成功")
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    finish();
                                                }
                                            });

                                        } else {
                                            pDialog.setTitleText(jsonObject.getString("message"))
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        }

                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        // Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                        pDialog.setTitleText("未知错误")
                                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                                    }
                                });
                            }});
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Activity activity = RegisterActivity.this;
                                while (activity.getParent() != null) {
                                    activity = activity.getParent();
                                }
                                pDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("获取验证码成功");
                                pDialog.setCancelable(false);
                                pDialog.show();
                            }
                        });
                    }
                } else {
                    if (smsFlage==1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Activity activity = RegisterActivity.this;
                                while (activity.getParent() != null) {
                                    activity = activity.getParent();
                                }
                                pDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("获取验证码失败");
                                pDialog.setCancelable(false);
                                pDialog.show();
                            }
                        });
                    }else if (smsFlage==2){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.setTitleText("验证码错误")
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }
                        });
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(eh);

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            checkBtn.setClickable(false);
            checkBtn.setText(millisUntilFinished / 1000 + "秒重新发送");
        }

        @Override
        public void onFinish() {
            checkBtn.setText("获取验证码");
            checkBtn.setClickable(true);
        }
    }

    public void getCheckNum(View source) {
        String user_tele = sitele.getText().toString();
        if (user_tele.length() != 11) {
            Toast.makeText(getApplicationContext(), "请输入正确手机号",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //checkBtn.setEnabled(false);
        smsFlage=1;
        //获取验证码
        SMSSDK.getVerificationCode("86", sitele.getText().toString());
        //fuck
        time.start();
    }

}
