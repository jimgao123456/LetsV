package com.example.gao.letsv;

import android.os.Bundle;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.example.gao.letsv.MyListAdatper.Reading_Adapter;

/**
 * Created by dk150 on 2018/6/6.
 */

public class reading_FoldableListActivity extends reading_BaseActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_foldable_list);
        getSupportActiongBar().setDisplayHomeAsUpEnabled(true);

        FoldableListLayout foldableListLayout = Views.find(this,R.id.foldable_list);
        foldableListLayout.setAdapter(new Reading_Adapter(this));

    }
}
