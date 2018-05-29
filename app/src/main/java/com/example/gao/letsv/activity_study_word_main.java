package com.example.gao.letsv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView wordtitle=null;
    ImageView horn=null;
    MyScrollView myScrollView =null;
    private List<Map<String, Object>> dataList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_word);
        wordinformation_listview = (UnScrollListView) findViewById(R.id.study_word_listitem);
        wordtitle=(TextView) findViewById(R.id.studay_word_title);
        horn=(ImageView)findViewById(R.id.study_word_horn);
        myScrollView=(MyScrollView)findViewById(R.id.studay_word_MyScrollView);
        wordinformation_listview.setFocusable(false);
        initDataList();//初始化数据

    }

    private void initDataList() {
        wordtitle.setText(Html.fromHtml("<font color='#87CEFA'>per</font><font color='#7B68EE'>sist</font>"));
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titleview", "我是魔鬼"+i);
            map.put("explainview", "不许笑" + i);
            dataList.add(map);
        }
        MyAdapter_study_main adapter = new MyAdapter_study_main(this, dataList, R.layout.study_word_main_listitem);
        wordinformation_listview.setAdapter(adapter);

    }
}
