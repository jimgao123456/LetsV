package com.example.gao.letsv;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_0 extends Fragment {

    public Fragment_Homepage_0() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_0, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        QMUIRoundButton btn_startstudy= (QMUIRoundButton)view.findViewById(R.id.button_startstudy);
        btn_startstudy.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {
               Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
               startActivity(mainIntent);
           }
       });
    }
}
