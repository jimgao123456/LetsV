package com.example.gao.letsv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gao.letsv.MyListAdatper.MyAdapter_study_main;
import com.example.gao.letsv.MyWidget.MyScrollView;
import com.example.gao.letsv.MyWidget.UnScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gangchang on 2018/5/27.
 */

public class activity_study_word_main extends AppCompatActivity {

    UnScrollListView wordinformation_listview = null;
    TextView wordtitle = null;
    ImageView horn = null;
    MyScrollView myScrollView = null;
    ImageView word_add=null;
    ImageView word_collection=null;
    private List<Map<String, Object>> dataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_word);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenHeight = outMetrics.heightPixels;
        int screenWidth = outMetrics.widthPixels;
        wordinformation_listview = (UnScrollListView) findViewById(R.id.study_word_listitem);
        wordtitle = (TextView) findViewById(R.id.studay_word_title);
        horn = (ImageView) findViewById(R.id.study_word_horn);
        myScrollView = (MyScrollView) findViewById(R.id.studay_word_MyScrollView);
        wordinformation_listview.setFocusable(false);
        word_add=(ImageView)findViewById(R.id.myadapter_study_button_add) ;
        word_collection=(ImageView)findViewById(R.id.myadapter_study_button_collection);

        //设置scrollview的高度
        ViewGroup.LayoutParams params = myScrollView.getLayoutParams();
        params.height=screenHeight*9/10;
        myScrollView.setLayoutParams(params);

        //设置下方两个按钮的高度
       params = word_add.getLayoutParams();
        params.height=screenHeight/10;
        params.width=screenWidth/2;
        word_add.setLayoutParams(params);
        params = word_collection.getLayoutParams();
        params.height=screenHeight/10;
        params.width=screenWidth/2;
        word_collection.setLayoutParams(params);

        initDataList();//初始化数据

    }

    private void initDataList() {
        wordtitle.setText(Html.fromHtml("<font color='#87CEFA'>per</font><font color='#7B68EE'>sist</font>"));
        dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titleview", "基础例句");
        map.put("explainview", "<font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font><br><font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font>");
        dataList.add(map);
        map = new HashMap<String, Object>();
        map.put("titleview", "基础例句");
        map.put("explainview", "<font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font><br><font color='#cccccc'>I hope you'll persist in your efforts.</font><font color='#8f8f8f'>   [来自官方]</font>");
        dataList.add(map);
        MyAdapter_study_main adapter = new MyAdapter_study_main(this, dataList, R.layout.study_word_main_listitem);
        wordinformation_listview.setAdapter(adapter);

    }
}
