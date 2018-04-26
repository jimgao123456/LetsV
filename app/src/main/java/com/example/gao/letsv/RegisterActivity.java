package com.example.gao.letsv;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Button  finisires=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.siname = (EditText)super.findViewById(R.id.signname);
        this.sipassword = (EditText)super.findViewById(R.id.sign_password);
        this.sipassword2 = (EditText)super.findViewById(R.id.sign_password2);
        this.sitele = (EditText)super.findViewById(R.id.sign_tele);
        this.checknum = (EditText)super.findViewById(R.id.checknumber);
        this.nameTips = (TextView)super.findViewById(R.id.nametip);
        this.passwordTips = (TextView)super.findViewById(R.id.passwordtip);
        this.passwordTips2 = (TextView)super.findViewById(R.id.passwordtip2);
        this.teleTips = (TextView)super.findViewById(R.id.teletip);
        sipassword.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if(sipassword.getText().toString().length()<6) {
                    passwordTips.setText("密码不得小于六位");
                }else if(sipassword.getText().toString().length()>18){
                    passwordTips.setText("密码不得大于18位");
                }else if(sipassword2.getText().toString().equals(sipassword.getText().toString())){
                    passwordTips2.setVisibility(View.INVISIBLE);
                } else{
                    passwordTips.setText(" ");
                }
                return false;
            }
        });
        //TODO;添加密码焦点框监听
        sipassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
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
                if(hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if(sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                        passwordTips2.setVisibility(View.VISIBLE);
                    }else{
                        passwordTips2.setVisibility(View.INVISIBLE);
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
                if(sipassword2.getText().toString().equals(sipassword.getText().toString())) {
                    passwordTips2.setVisibility(View.INVISIBLE);
                }else{
                    passwordTips2.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        //TODO:添加用户名输入框监听
        siname.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if(siname.getText().toString().length()<6) {
                    nameTips.setText("用户名不得小于六位");
                }else if(siname.getText().toString().length()>18){
                    nameTips.setText("用户名不得大于18位");
                }else if(!isusername(siname.getText().toString())) {
                    nameTips.setText("用户名只能由数字和英文字母组成");
                }else{
                    nameTips.setText(" ");
                }
                return false;
            }
        });
        //TODO：添加用户名焦点监听
        siname.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
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
                if(sitele.getText().toString().length() != 11) {
                    teleTips.setText("请输入正确的手机号");
                    teleTips.setVisibility(View.VISIBLE);
                }else{
                    teleTips.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });


        final QMUIRoundButton circularButton1 = (QMUIRoundButton) findViewById(R.id.btn_register);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
