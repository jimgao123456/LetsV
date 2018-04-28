package com.example.gao.letsv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dd.processbutton.iml.SubmitProcessButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by gangchang on 2018/4/27.
 */

public class activity_forgetpassword_inputphone extends AppCompatActivity {

    EditText phonenumber = null;
    EditText checknumber = null;
    Button checkbutton = null;
    SubmitProcessButton nextbutton = null;

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
                    SweetAlertDialog pDialog = new SweetAlertDialog(activity_forgetpassword_inputphone.this, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("成功");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }
            }
        });
    }
}
