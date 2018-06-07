package com.example.gao.letsv;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by dk150 on 2018/5/29.
 */

public class activity_userpage_setting extends AppCompatActivity {
    //声明控件
    private AudioManager audioManager;
    private int currentVolume,maxVolume;
    com.suke.widget.SwitchButton sounds=null;
    com.suke.widget.SwitchButton vibrate=null;
    SeekBar volume =null;
    Spinner quality=null;
    String[] arr = this.getResources().getStringArray(R.array.picquality);
    String pic = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage_setting);
        File file = new File(getCacheDir().getPath(), "setting.properties");
        if(file.exists()){
            Properties prop =new Properties();
            try{
                FileInputStream s = new FileInputStream(file);
                prop.load(s);
                //玄学转化！
                Boolean a = Boolean.valueOf(prop.getProperty("sounds")).booleanValue();
                Boolean b = Boolean.valueOf(prop.getProperty("vibrate")).booleanValue();
                sounds.setChecked(a);
                vibrate.setChecked(b);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
       initView();
    }

    private void initView(){
        //通过控件ID获得控件对象
        sounds = (com.suke.widget.SwitchButton)findViewById(R.id.activity_setting_sounds);
        vibrate = (com.suke.widget.SwitchButton)findViewById(R.id.activity_setting_vibrate);
        volume = (SeekBar)findViewById(R.id.activity_setting_volume);
        quality = (Spinner)findViewById(R.id.activity_choose_quality);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        volume.setMax(maxVolume);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        volume.setProgress(currentVolume);

        sounds.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"已经可以发出声音了！",Toast.LENGTH_SHORT).show();
                    try {
                        File file = new File(getCacheDir().getPath(), "setting.properties");
                        if (!file.exists())
                            file.createNewFile();
                        FileOutputStream fo = new FileOutputStream(file);
                        Properties properties = new Properties();
                        properties.put("sounds", true);
                        properties.store(fo, "");
                        fo.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"唔！唔唔唔！！唔……",Toast.LENGTH_LONG).show();
                    try {
                        File file = new File(getCacheDir().getPath(), "setting.properties");
                        if (!file.exists())
                            file.createNewFile();
                        FileOutputStream fo = new FileOutputStream(file);
                        Properties properties = new Properties();
                        properties.put("sounds", false);
                        properties.store(fo, "");
                        fo.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        vibrate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"Emmmm……现在是振动状态的说……",Toast.LENGTH_SHORT).show();
                    try {
                        File file = new File(getCacheDir().getPath(), "setting.properties");
                        if (!file.exists())
                            file.createNewFile();
                        FileOutputStream fo = new FileOutputStream(file);
                        Properties properties = new Properties();
                        properties.put("vibrate", true);
                        properties.store(fo, "");
                        fo.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"诶~你竟然把振动关掉了~",Toast.LENGTH_LONG).show();
                    try {
                        File file = new File(getCacheDir().getPath(), "setting.properties");
                        if (!file.exists())
                            file.createNewFile();
                        FileOutputStream fo = new FileOutputStream(file);
                        Properties properties = new Properties();
                        properties.put("vibrate", false);
                        properties.store(fo, "");
                        fo.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,progress,0);
                //获取当前音量
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                //设置当前音量
                volume.setProgress(currentVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        quality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"已选中",Toast.LENGTH_SHORT).show();
                pic = quality.getSelectedItem().toString();
                try {
                    File file = new File(getCacheDir().getPath(), "setting.properties");
                    if (!file.exists())
                        file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    Properties properties = new Properties();
                    properties.put("quality", true);
                    properties.store(fo, "");
                    fo.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
            }
        });
    }
}
