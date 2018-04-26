package com.example.gao.letsv;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class MainActivity extends AppCompatActivity {
    @Titles
    private static final String[] mTitles = {"首页", "社区", "商城", "个人中心"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0, R.drawable.homepage_fragment_0_0};

    private ViewPager mVp;
    private JPTabBar mTabBar;
    Fragment[] fragmentarray = new Fragment[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = (ViewPager) findViewById(R.id.homepage_vp);
        mTabBar = (JPTabBar) findViewById(R.id.tabbar);

        //切换
        fragmentarray[0] = new Fragment_Homepage_0();
        fragmentarray[1] = new Fragment_Homepage_1();
        fragmentarray[2] = new Fragment_Homepage_2();
        fragmentarray[3] = new Fragment_Homepage_3();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        mTabBar.setContainer(mVp);
    }

    //Fragment适配器
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = fragmentarray[0];
                    break;
                case 1:
                    fragment = fragmentarray[1];
                    break;
                case 2:
                    fragment = fragmentarray[2];
                    break;
             case 3:
                  fragment = fragmentarray[3];
                break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            return 4;
        }
    }
}
