package com.example.gao.letsv;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gao.letsv.LoginViews.LoginActivity;
import com.example.gao.letsv.Studyword.activity_study_word_grouplist;
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
//              Intent intent = new Intent(getActivity(), Ps.class);
//                    startActivity(intent);
              if(MainActivity.autologin!=true) {
//                  String[] group1 = {"include", "conclude", "seclude", "exclude", "preclude", "occlude"};
//                  String[] group2 = {"attain", "obtain", "retain", "sustain", "maintain"};
//                  String[] group3 = {"resist", "consist", "subsist", "persist", "desist"};
//                  int nGroup = 1;
//                  int nNum = 1;
//                  Bundle mBundle = new Bundle();
//                  mBundle.putStringArray("group1", group1);
//                  mBundle.putStringArray("group2", group2);
//                  mBundle.putStringArray("group3", group3);
//                  mBundle.putInt("ngroup", nGroup);
//                  mBundle.putInt("nnum", nNum);
//                  Intent mainIntent = new Intent(getActivity(), PhraseActivity.class);
//                  mainIntent.putExtras(mBundle);
//                  startActivity(mainIntent);

                      Intent intent = new Intent(getActivity(), activity_study_word_grouplist.class);
                      startActivity(intent);

              }else{
                  Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
                  startActivity(mainIntent);
              }
           }
       });
    }
}
