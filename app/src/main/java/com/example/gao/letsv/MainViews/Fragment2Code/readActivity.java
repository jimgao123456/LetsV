package com.example.gao.letsv.MainViews.Fragment2Code;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class readActivity extends AppCompatActivity {

//    private Button mBtnRecordAudio;
//    private Button mBtnPlayAudio;

    private QMUIRoundButton mBtnRecordAudio;
//    private QMUIRoundButton mBtnPlayAudio;
    private TextView nowText;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        JSONObject jsonObject = JSONObject.parseObject(content);
        Map<String,String> map = new TreeMap<String,String>();
        Set<String> keys = (Set<String>) jsonObject.keySet();
        for (String key : keys){
            map.put(key,jsonObject.getString(key));
        }
        Map<String, String> resultMap = sortMapByKey(map);
        List<String> wenzhang = MtoL(resultMap);

        setContentView(R.layout.activity_read);
        initView();
        nowText.setText(wenzhang.get(position));
        mBtnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position ++;
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
                    @Override
                    public void onCancel() {
                        position --;
                        fragment.dismiss();
                    }
                });
                nowText.setText(position);
            }
        });

//        mBtnPlayAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RecordingItem recordingItem = new RecordingItem();
//                SharedPreferences sharePreferences = getSharedPreferences("sp_name_audio", MODE_PRIVATE);
//                final String filePath = sharePreferences.getString("audio_path", "");
//                long elpased = sharePreferences.getLong("elpased", 0);
//                recordingItem.setFilePath(filePath);
//                recordingItem.setLength((int) elpased);
//                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
//                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
//            }
//        });
    }

    private void initView() {
//        mBtnRecordAudio = (Button)findViewById(R.id.read_btn_record_sound);
//        mBtnPlayAudio = (Button) findViewById(R.id.read_btn_play_sound);
        mBtnRecordAudio = (QMUIRoundButton) findViewById(R.id.read_btn_record_sound);
        nowText = (TextView)findViewById(R.id.activity_read_text);
//        mBtnPlayAudio = (QMUIRoundButton) findViewById(R.id.read_btn_play_sound);
    }


    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    public List<String> MtoL(Map<String ,String> map){
        List<String> mylist= new ArrayList<String>();
        for (String value : map.values())
        {
            mylist.add(value);
        }
        return mylist;
    }
}
