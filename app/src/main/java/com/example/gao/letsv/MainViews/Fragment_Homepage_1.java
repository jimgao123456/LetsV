package com.example.gao.letsv.MainViews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import com.example.gao.letsv.R;
import com.example.gao.letsv.reading_UnfoldableDetailsActivity;
import com.example.gao.letsv.testView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_1 extends Fragment {

    public Fragment_Homepage_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_1, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)  {

    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button button = (Button)getActivity().findViewById(R.id.reading_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),reading_UnfoldableDetailsActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    }
