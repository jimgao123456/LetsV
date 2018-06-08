package com.example.gao.letsv;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;

/**
 * Created by dk150 on 2018/6/6.
 */

public class reading_UnfoldableDetailsActivity extends reading_BaseActivity {

    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_unfoldable_list);
        getSupportActiongBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = Views.find(this,R.id.list_view);
    }
}
