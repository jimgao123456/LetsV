package com.example.gao.letsv.MainViews;
/**
 * ......................我佛慈悲....................
 * ......................_oo0oo_.....................
 * .....................o8888888o....................
 * .....................88" . "88....................
 * .....................(| -_- |)....................
 * .....................0\  =  /0....................
 * ...................___/`---'\___..................
 * ..................' \\|     |// '.................
 * ................./ \\|||  :  |||// \..............
 * .............../ _||||| -卍-|||||- \..............
 * ..............|   | \\\  -  /// |   |.............
 * ..............| \_|  ''\---/''  |_/ |.............
 * ..............\  .-\__  '-'  ___/-. /.............
 * ............___'. .'  /--.--\  `. .'___...........
 * .........."" '<  `.___\_<|>_/___.' >' ""..........
 * ........| | :  `- \`.;`\ _ /`;.`/ - ` : | |.......
 * ........\  \ `_.   \_ __\ /__ _/   .-` /  /.......
 * ....=====`-.____`.___ \_____/___.-`___.-'=====....
 * ......................`=---='.....................
 * ..................佛祖开光 ,永无BUG................
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.LoginViews.LoginActivity;
import com.example.gao.letsv.MainViews.Fragment0Code.Fragment_Homepage_0;
import com.example.gao.letsv.MainViews.Fragment1Code.Fragment_Homepage_1;
import com.example.gao.letsv.MainViews.Fragment1Code.GlideHelper;
import com.example.gao.letsv.MainViews.Fragment1Code.Painting;
import com.example.gao.letsv.MainViews.Fragment2Code.Fragment_Homepage_2;
import com.example.gao.letsv.MainViews.Fragment3Code.Fragment_Homepage_3;
import com.example.gao.letsv.MyListAdatper.MyAdapter_study_main;
import com.example.gao.letsv.R;
import com.example.gao.letsv.Search_views.RecordAudioDialogFragment;
import com.example.gao.letsv.Search_views.search_word_result_bykeyboard;
import com.example.gao.letsv.Studyword.activity_study_word_grouplist;
import com.example.gao.letsv.Studyword.activity_study_word_main;
import com.example.gao.letsv.Studyword.activity_study_word_test;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {
    @Titles
    private static final String[] mTitles = {"单词", "阅读", "听说", "我"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.homepage_fragment_0_1, R.drawable.homepage_fragment_1_1, R.drawable.homepage_fragment_2_1, R.drawable.homepage_fragment_3_1};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_1_0, R.drawable.homepage_fragment_2_0, R.drawable.homepage_fragment_3_0};

    private static ViewPager mVp;
    public static JPTabBar mTabBar;
    private EditText toptext;

    public static String username = null;
    public static String password = null;
    public static String nickname = null;

    public static boolean haschecklogin = false;//控制只在程序第一次运行的时候登录
    public static boolean state_login = false;//控制是否登录成功
    public static String num = "0";
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static int nowplane = 0;

    public static String serverip = "http://139.199.110.17:8888/";

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

    //fragment1--真题需要的一些控件
    public static UnfoldableView unfoldableView = null;
    public static View listTouchInterceptor = null;
    public static View detailsLayout = null;

    Fragment[] fragmentarray = new Fragment[4];


    //拍照
    private Uri imageUri;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = (ViewPager) findViewById(R.id.homepage_vp);
        mVp.setOffscreenPageLimit(0);
        mTabBar = (JPTabBar) findViewById(R.id.tabbar);

        //  mTabBar.setTabListener(this);
        toptext = (EditText) findViewById(R.id.homepage_edittext);
        findViewById(R.id.activity_main_layout).setClickable(false);
        List<String> permissionsNeeded = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CHANGE_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_TASKS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.GET_TASKS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionsNeeded.size() != 0) {
            String[] permission = new String[permissionsNeeded.size()];
            for (int i = 0; i < permissionsNeeded.size(); i++) {
                permission[i] = permissionsNeeded.get(i);
            }
            ActivityCompat.requestPermissions(this, permission, 1);
        }

        //语音识别
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b276195");

        //屏幕宽高
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenHeight = outMetrics.heightPixels;
        screenWidth = outMetrics.widthPixels;
        //切换
        fragmentarray[0] = new Fragment_Homepage_0();
        fragmentarray[1] = new Fragment_Homepage_1();
        fragmentarray[2] = new Fragment_Homepage_2();
        fragmentarray[3] = new Fragment_Homepage_3();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        mTabBar.setContainer(mVp);
        mTabBar.setTabListener(this);
        View middleView = mTabBar.getMiddleView();
        middleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 用于PopupWindow的View
                View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_for_words, null);
                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点

                PopupWindow window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //window.setContentView(contentView);
                NiceSpinner niceSpinner = null;
                niceSpinner = (NiceSpinner) contentView.findViewById(R.id.search_words_nice_spinner);
                List<String> dataset = new LinkedList<>(Arrays.asList("英汉", "汉英"));
                niceSpinner.attachDataSource(dataset);
                niceSpinner.setSelectedIndex(0);
                niceSpinner.setTextSize(18);
                niceSpinner.setBackgroundResource(getResources().getIdentifier("selector_search_for_words_spinner", "drawable", getClass().getPackage().getName()));

                //语音查词
                ImageView imageButoon_searchByAudio = (ImageView) contentView.findViewById(R.id.search_words_nice_searchword_voice);
                imageButoon_searchByAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
//                        fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
//                        fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
//                            @Override
//                            public void onCancel() {
//                                fragment.dismiss();
//                            }
//                        });

                        //1.创建 RecognizerDialog 对象
                        RecognizerDialog mDialog = new RecognizerDialog(MainActivity.this, mInitListener);
                        mDialog.setListener(mRecognizerDialogListener);
                       // mDialog.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
                        mDialog.setParameter(SpeechConstant.LANGUAGE, "en_us");
                        mDialog.setParameter(SpeechConstant.ACCENT, null);
                        mDialog.show();
                        TextView txt =(TextView)mDialog.getWindow().getDecorView().findViewWithTag("textlink");
                        txt.setText("");
                    }
                });

                //文字查词
                EditText inputText = (EditText) contentView.findViewById(R.id.search_words_nice_input_Word);
                inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            searchword_keyboard(inputText.getText().toString().trim());
                        }
                        return false;
                    }
                });

                //拍照查词
                ImageView imageButoon_searchByPhoto = (ImageView) contentView.findViewById(R.id.search_words_nice_searchword_photo);
                imageButoon_searchByPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cachePath = null;
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera, 0);
                        window.dismiss();
                    }
                });


                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(toptext, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移

                //window.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (unfoldableView != null
                && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
            unfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        final ImageView image = Views.find(detailsLayout, R.id.homepage_f1_details_image);
        final TextView title = Views.find(detailsLayout, R.id.homepage_f1_details_title);
        final TextView description = Views.find(detailsLayout, R.id.homepage_f1_details_text);


        SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("拉取文章中");
        pDialog.setCancelable(false);
        pDialog.show();
        AsyncHttpClient client = new AsyncHttpClient(8888);
        RequestParams params = new RequestParams();
        params.put("passageid", painting.getPassageId());
        String url = MainActivity.serverip + "/passagecontent";

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                JSONObject jsonObject = JSONObject.parseObject(str);
                painting.context = jsonObject.getString("content");
//                Log.e("1",painting.getPassageId());
                pDialog.cancel();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GlideHelper.loadPaintingImage(image, painting);
                        title.setText(painting.getTitle());

//                        SpannableBuilder builder = new SpannableBuilder(getApplicationContext());
//                        builder
//                                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
//                                .append(painting.getTitle()).append("\n")
//                                .clearStyle()
//                                .createStyle().setFont(Typeface.NORMAL).apply()
//                                .append(painting.getContext())
//                                .clearStyle();
                        description.setText(painting.getContext());
                        unfoldableView.unfold(coverView, detailsLayout);
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                pDialog.setTitleText("未知错误")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (!haschecklogin) {
                //加载保存的密码
                File file = new File(getCacheDir().getPath(), "config.properties");
                if (file.exists()) {
                    SweetAlertDialog pDialog_login = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog_login.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog_login.setTitleText("登录中");
                    pDialog_login.setCancelable(false);
                    pDialog_login.show();
                    Properties prop = new Properties();
                    try {
                        FileInputStream s = new FileInputStream(file);
                        prop.load(s);
                        username = prop.get("username").toString();
                        password = prop.get("password").toString();
                        AsyncHttpClient client = new AsyncHttpClient(8888);
                        //封装需要传递的参数
                        RequestParams params = new RequestParams();
                        params.put("username", username);
                        params.put("password", password);
                        String url = serverip + "login";

                        client.post(url, params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                findViewById(R.id.activity_main_layout).setClickable(true);
                                String str = new String(responseBody);
                                JSONObject jsonObject = JSONObject.parseObject(str);
                                int state = jsonObject.getInteger("state");
                                if (state == 0) {
                                    nickname = jsonObject.getString("nickname");
                                    nowplane = jsonObject.getInteger("plan");
                                    state_login = true;
                                    if (Fragment_Homepage_0.planetext != null) {
                                        switch (nowplane) {
                                            case 0:
                                                Fragment_Homepage_0.planetext.setText("学习计划:四级");
                                                break;
                                            case 1:
                                                Fragment_Homepage_0.planetext.setText("学习计划:六级");
                                                break;
                                            case 2:
                                                Fragment_Homepage_0.planetext.setText("学习计划:雅思/托福");
                                                break;
                                        }
                                    }

                                    pDialog_login.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog_login.setTitleText(nickname + " 欢迎回来!");
//                                    Toast.makeText(getApplicationContext(), "欢迎回来！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
                                    MainActivity.this.startActivity(mainIntent);
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                // Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                pDialog_login.cancel();
                                findViewById(R.id.activity_main_layout).setClickable(true);
                                username = null;
                                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                                MainActivity.this.startActivity(mainIntent);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //从未登录过
                    findViewById(R.id.activity_main_layout).setClickable(true);
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
            }
            haschecklogin = true;
        }
    }

    @Override
    public void onTabSelect(int index) {

    }

    @Override
    public boolean onInterruptSelect(int index) {
        if (state_login)
            return false;
        else
            return true;
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

    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                //相机拍照结束
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bundle bundle = data.getExtras();
                    //获取相机返回的数据，并转换为图片格式
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    savePic(bitmap);
                    //  iv.setImageBitmap(bitmap);//显示图片
                }
        }
    }

    private void searchword_keyboard(String word) {
        SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在拉取单词信息");
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient(8888);
        RequestParams params = new RequestParams();
        params.put("word", word);
        String url = serverip + "studyword";

        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.cancel();
                String str = new String(responseBody);
                JSONObject jsonObject=JSON.parseObject(str);
                if(jsonObject.getString("meaning").equals("")){
                    SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("后台偷懒了，没收录 "+word+" 这个单词");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else {
                    Intent intent = new Intent(MainActivity.this, search_word_result_bykeyboard.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("json", str);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                pDialog.setTitleText("未知错误")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @return
     */
    private String savePic(Bitmap bitmap) {
        String sdState = Environment.getExternalStorageState();
        if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";

        FileOutputStream fout = null;
        File file = new File("/sdcard/pintu/");
        file.mkdirs();
        String filename = file.getPath() + name;
        try {
            fout = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.flush();
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            //  Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                // showTip("初始化失败，错误码：" + code);
                SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("初始化失败");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        }
    };
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            JSONObject jsonObject = JSON.parseObject(results.getResultString());
            //编号
            if(jsonObject.getString("sn").equals("1")) {
                JSONArray jsonArray=JSON.parseArray(jsonObject.getString("ws"));
                String temp=jsonArray.getJSONObject(0).getString("cw");
                JSONObject jsonObject_words=JSON.parseObject(temp.substring(1,temp.length()-1));
                String word = jsonObject_words.getString("w");
                SweetAlertDialog pDialog2 = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog2.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog2.setTitleText("正在拉取单词信息");
                pDialog2.setCancelable(false);
                pDialog2.show();

                AsyncHttpClient client = new AsyncHttpClient(8888);
                RequestParams params = new RequestParams();
                params.put("word", word);
                String url = serverip + "studyword";

                client.post(url, params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        pDialog2.cancel();
                        String str1 = new String(responseBody);
                        JSONObject jsonObject1=JSON.parseObject(str1);
                        if(jsonObject1.getString("meaning")==null || jsonObject1.getString("meaning").equals("")){
                            SweetAlertDialog pDialog1 = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                            pDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog1.setTitleText("后台偷懒了，没收录 "+word+" 这个单词");
                            pDialog1.setCancelable(false);
                            pDialog1.show();
                        }else {
                            Intent intent = new Intent(MainActivity.this, search_word_result_bykeyboard.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("json", str1);
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        pDialog2.setTitleText("未知错误")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("语音识别未知错误");
            pDialog.setCancelable(false);
            pDialog.show();
        }

    };

}
