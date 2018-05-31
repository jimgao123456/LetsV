package com.example.gao.letsv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gangchang on 2018/5/30.
 */

public class search_for_words extends AppCompatActivity {
    NiceSpinner niceSpinner = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_words);
        niceSpinner = (NiceSpinner) findViewById(R.id.search_words_nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("英汉", "汉英"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setSelectedIndex(0);
        niceSpinner.setTextSize(18);
        niceSpinner.setBackgroundResource(getResources().getIdentifier("search_for_words_spinner_selector", "drawable", getClass().getPackage().getName()));
    }
}
