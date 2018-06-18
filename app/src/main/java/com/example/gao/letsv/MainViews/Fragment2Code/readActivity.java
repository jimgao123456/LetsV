package com.example.gao.letsv.MainViews.Fragment2Code;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.gao.letsv.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class readActivity extends AppCompatActivity {

//    private Button mBtnRecordAudio;
//    private Button mBtnPlayAudio;

    private QMUIRoundButton mBtnRecordAudio;
//    private QMUIRoundButton mBtnPlayAudio;
    private TextView nowText;
    private int position = 0;
    static private String nowsen;
    private static List<String> wenzhang;
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
        wenzhang = MtoL(resultMap);

        setContentView(R.layout.activity_read);    initView();

        nowText.setText(wenzhang.get(position));
        nowsen = wenzhang.get(position);
        mBtnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.创建 RecognizerDialog 对象
                RecognizerDialog mDialog = new RecognizerDialog(readActivity.this, mInitListener);
                mDialog.setListener(mRecognizerDialogListener);
                // mDialog.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
                mDialog.setParameter(SpeechConstant.LANGUAGE, "en_us");
                mDialog.setParameter(SpeechConstant.ACCENT, null);
                mDialog.show();
                TextView txt =(TextView)mDialog.getWindow().getDecorView().findViewWithTag("textlink");
                txt.setText("");

//                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
//                fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
//                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
//                    @Override
//                    public void onCancel() {
//                        position --;
//                        fragment.dismiss();
//                    }
//                });

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



    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            //  Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                // showTip("初始化失败，错误码：" + code);
                SweetAlertDialog pDialog = new SweetAlertDialog(readActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                String sentence ="";
                JSONArray jsonArray=JSON.parseArray(jsonObject.getString("ws"));
                for(int i=0;i<jsonArray.size();i++) {

                    String temp = jsonArray.getJSONObject(i).getString("cw");
                    JSONObject jsonObject_words = JSON.parseObject(temp.substring(1, temp.length() - 1));
                    String word = jsonObject_words.getString("w");
                    sentence=sentence+" "+word;
                }
                sentence=sentence.substring(1);
                //原句比较
                SweetAlertDialog pDialog1 = new SweetAlertDialog(readActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog1.setTitleText("正在对比相似度");
                pDialog1.setCancelable(false);
                pDialog1.show();
                float sim = getSimilarityRatio(sentence,nowsen);
                pDialog1.cancel();
                pDialog1.setCancelable(true);
                pDialog1.setTitleText("得分:"+(int)sim+"分");
                //调笑一句或结束窗口
                if (position<wenzhang.size()) {
                    position++;
                    nowText.setText(wenzhang.get(position));
                    nowsen = wenzhang.get(position);
                }else{
                    finish();
                }
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            SweetAlertDialog pDialog = new SweetAlertDialog(readActivity.this, SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("语音识别未知错误");
            pDialog.setCancelable(false);
            pDialog.show();
        }

    };

    private int compare(String str, String target)
    {
        int d[][];              // 矩阵
        int n = str.length();
        int m = target.length();
        int i;                  // 遍历str的
        int j;                  // 遍历target的
        char ch1;               // str的
        char ch2;               // target的
        int temp;               // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) { return m; }
        if (m == 0) { return n; }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++)
        {                       // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++)
        {                       // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++)
        {                       // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++)
            {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2+32 || ch1+32 == ch2)
                {
                    temp = 0;
                } else
                {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private int min(int one, int two, int three)
    {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     */

    public float getSimilarityRatio(String str, String target)
    {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }
}
