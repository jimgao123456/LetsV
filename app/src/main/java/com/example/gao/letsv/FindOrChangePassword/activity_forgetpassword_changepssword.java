package com.example.gao.letsv.FindOrChangePassword;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.dd.processbutton.iml.SubmitProcessButton;
import com.example.gao.letsv.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * Created by gangchang on 2018/5/11.-
 */

public class activity_forgetpassword_changepssword extends AppCompatActivity {
    private EditText password1=null;
    private EditText password2=null;
    private SubmitProcessButton submitProcessButton=null;
    private  String phonenumber=null;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phonenumber = getIntent().getExtras().getString("phone");
        password1=(EditText)findViewById(R.id.forget_change_password);
        password2=(EditText)findViewById(R.id.forget_change_password2);
        submitProcessButton=(SubmitProcessButton)findViewById(R.id.forget_change_subbutton);
        submitProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password1.getText().toString().length()<6 || password1.getText().toString().length()>18){
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_changepssword.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("请输入正确密码");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else if(password2.getText().toString().equals(password1.getText().toString())==false){
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_changepssword.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("两次密码输入不一致");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else{
                    //提交
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_changepssword.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    //封装需要传递的参数
                    RequestParams params = new RequestParams();
                    params.put("username", phonenumber);
                    params.put("password", password1.getText().toString());
                    String url = "http://58.87.108.125:8080/forget";
                    client.post(url, params, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String str = new String(responseBody);
                            JSONObject jsonObject = JSONObject.parseObject(str);
                            int state = jsonObject.getInteger("state");
                            if (state == 0) {
                                //修改密码成功
                                pDialog.setTitleText("修改密码成功")
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                            } else {
//                                pDialog.setTitleText("密码错误")
//                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            pDialog.setTitleText("密码修改失败，请稍后重试")
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                        }
                    });
                }

            }
        });
    }
}
