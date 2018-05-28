package com.example.gao.letsv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    @Titles
    private static final String[] mTitles = {"首页", "社区", "商城", "个人中心"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0};

    private ViewPager mVp;
    private JPTabBar mTabBar;

    public static  String username=null;
    public static String password=null;
    public static boolean autologin=false;
    public static boolean haschecklogin=false;
    public static String num="0";

    Fragment[] fragmentarray = new Fragment[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = (ViewPager) findViewById(R.id.homepage_vp);
        mTabBar = (JPTabBar) findViewById(R.id.tabbar);
        List<String> permissionsNeeded = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add( Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add( Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.GET_TASKS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.GET_TASKS);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(permissionsNeeded.size()!=0){
            String[] permission = new  String [permissionsNeeded.size()];
            for(int i=0;i<permissionsNeeded.size();i++){
                permission[i]=permissionsNeeded.get(i);
            }
            ActivityCompat.requestPermissions(this, permission, 1);
        }
        //切换
        fragmentarray[0] = new Fragment_Homepage_0();
        fragmentarray[1] = new Fragment_Homepage_1();
        fragmentarray[2] = new Fragment_Homepage_2();
        fragmentarray[3] = new Fragment_Homepage_3();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        mTabBar.setContainer(mVp);



    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (!haschecklogin) {
                //加载保存的密码
                File file = new File(getCacheDir().getPath(), "config.properties");
                if (file.exists()) {
                    Properties prop = new Properties();
                    try {
                        FileInputStream s = new FileInputStream(file);
                        prop.load(s);
                        username = prop.get("username").toString();
                        password = prop.get("password").toString();
                        AsyncHttpClient client = new AsyncHttpClient();
                        //封装需要传递的参数
                        RequestParams params = new RequestParams();
                        params.put("username", username);
                        params.put("password", password);
                        String url = "http://58.87.108.125:8888/login";
                        client.post(url, params, new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String str = new String(responseBody);
                                JSONObject jsonObject = JSONObject.parseObject(str);
                                int state = jsonObject.getInteger("state");
                                if (state == 0) {
                                    autologin = true;
                                    Toast.makeText(getApplicationContext(), "欢迎回来！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
                                    MainActivity.this.startActivity(mainIntent);
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                // Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                                MainActivity.this.startActivity(mainIntent);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //从未登录过
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
            }
            haschecklogin=true;
        }
    }
    //Fragment适配器
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = fragmentarray[0];
                    break;
                case 1:
                    fragment = fragmentarray[1];
                    break;
                case 2:
                    fragment = fragmentarray[2];
                    break;
             case 3:
                  fragment = fragmentarray[3];
                break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            return 4;
        }
    }
}
