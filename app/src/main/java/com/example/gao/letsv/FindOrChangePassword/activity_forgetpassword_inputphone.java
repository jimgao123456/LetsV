package com.example.gao.letsv.FindOrChangePassword;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.example.gao.letsv.R;
import com.mob.MobSDK;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by gangchang on 2018/4/27.
 */

public class activity_forgetpassword_inputphone extends AppCompatActivity {

    EditText phonenumber = null;
    EditText checknumber = null;
    Button checkbutton = null;
    SubmitProcessButton nextbutton = null;
    private EventHandler eh;
    public SweetAlertDialog pDialog=null;
    public int smsFlage = 0;//0:设置为初始化值 1：请求获取验证码 2：提交用户输入的验证码判断是否正确
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_inputphone);
        phonenumber = (EditText) findViewById(R.id.forget_inputp_phonenumber);
        checknumber = (EditText) findViewById(R.id.forget_inputp_checknumber);
        checkbutton = (Button) findViewById(R.id.forget_inputp_checkbutton);
        nextbutton = (SubmitProcessButton) findViewById(R.id.forget_inputp_next);
        iniview();
    }
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

    private void iniview(){
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phonenumber.getText().toString().length()!=11){
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_inputphone.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确手机号");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else {
                    //获取验证码
                    smsFlage=1;
                    SMSSDK.getVerificationCode("86", phonenumber.getText().toString());
                }
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phonenumber.getText().toString().length()!=11){
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_inputphone.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确手机号");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else {
                    //判断验证码
                    smsFlage=2;
                    SMSSDK.submitVerificationCode("86", phonenumber.getText().toString(), checknumber.getText().toString());
//                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_inputphone.this, SweetAlertDialog.SUCCESS_TYPE);
//                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pDialog.setTitleText("成功");
//                    pDialog.setCancelable(false);
//                    pDialog.show();
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
                                //跳转到修改密码界面
                                Bundle mBundle = new Bundle();
                                mBundle.putString("phone", phonenumber.getText().toString());
                                Intent mainIntent = new Intent(activity_forgetpassword_inputphone.this, activity_forgetpassword_changepssword.class);
                                mainIntent.putExtras(mBundle);
                                startActivity(mainIntent);
                            }});
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Activity activity = activity_forgetpassword_inputphone.this;
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
                                Activity activity = activity_forgetpassword_inputphone.this;
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
                                Activity activity = activity_forgetpassword_inputphone.this;
                                while (activity.getParent() != null) {
                                    activity = activity.getParent();
                                }
                                pDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("验证码错误");
                                pDialog.setCancelable(false);
                                pDialog.show();
                            }
                        });
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }
}
