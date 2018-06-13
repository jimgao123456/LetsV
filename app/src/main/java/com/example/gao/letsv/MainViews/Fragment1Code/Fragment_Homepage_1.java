


package com.example.gao.letsv.MainViews.Fragment1Code;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.R;

public class Fragment_Homepage_1 extends Fragment {



    public Fragment_Homepage_1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_1, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ListView listView = view.findViewById(R.id.homepage_f1_list_view);
        listView.setAdapter(new PaintingsAdapter(getActivity()));


        MainActivity.listTouchInterceptor = view.findViewById(R.id.homepage_f1_touch_interceptor_view);
        MainActivity.listTouchInterceptor.setClickable(false);

        MainActivity.detailsLayout = view.findViewById(R.id.homepage_f1_details_layout);
        MainActivity.detailsLayout.setVisibility(View.INVISIBLE);

        MainActivity.unfoldableView = view.findViewById(R.id.homepage_f1_unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.fill_blank);
        MainActivity.unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        MainActivity.unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                MainActivity. listTouchInterceptor.setClickable(true);
                MainActivity.detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                MainActivity.listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                MainActivity. listTouchInterceptor.setClickable(false);
                MainActivity. detailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }



}





