package com.example.gao.letsv.MainViews.Fragment3Code;


import android.app.LauncherActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.example.gao.letsv.LoginViews.LoginActivity;
import com.example.gao.letsv.MainViews.Fragment2Code.activity_media_player;
import com.example.gao.letsv.MainViews.Fragment2Code.readActivity;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gangchang on 2018/4/25.
 */

public class Fragment_Homepage_3 extends Fragment {
    // 声明ListView控件
    private ListView mListView;
    private ArrayList<ListItem> mList;
    private CircleImageView circleImageView;

    public Fragment_Homepage_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.homepage_f3_listView);
        circleImageView=(CircleImageView)view.findViewById(R.id.homepage_f3_circlrimge);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles( "给伟大的程序猿点赞","注销")
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                switch (index) {
                                    case 0:
                                        //全文泛听
                                        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                        pDialog.setTitleText("万分感谢");
                                        pDialog.setCancelable(false);
                                        pDialog.show();
                                        break;
                                    case 1:
                                        //语音跟读
                                        MainActivity.username="";
                                        MainActivity.password="";
                                        MainActivity.state_login=false;
                                        MainActivity.mTabBar.setSelectTab(0);
                                        Intent mainIntent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(mainIntent);
                                        break;
                                }
                            }
                        }).show();
            }
        });
        // 获取Resources对象
        Resources res = this.getResources();

        mList = new ArrayList<ListItem>();
        // 初始化data，装载八组数据到数组链表mList中
        ListItem item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.dancileibie));
        item.setTitle("单词类别");
        mList.add(item);

        item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.xuexijindu));
        item.setTitle("学习进度");
        mList.add(item);

        item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.xuexijihua));
        item.setTitle("学习计划");
        mList.add(item);

        item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.cihuiceshi));
        item.setTitle("词汇测试");
        mList.add(item);

        item = new ListItem();
        item.setImage(res.getDrawable(R.drawable.lianxiwomen));
        item.setTitle("联系我们");
        mList.add(item);


        // 获取MainListAdapter对象
        MainListViewAdapter adapter = new MainListViewAdapter();

        // 将MainListAdapter对象传递给ListView视图
        mListView.setAdapter(adapter);
    }

    class ListItem {
        private Drawable image;
        private String title;

        public Drawable getImage() {
            return image;
        }

        public void setImage(Drawable image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    class MainListViewAdapter extends BaseAdapter {

        /**
         * 返回item的个数
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        /**
         * 返回item的内容
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }

        /**
         * 返回item的id
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /**
         * 返回item的视图
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;

            // 初始化item view
            if (convertView == null) {
                // 通过LayoutInflater将xml中定义的视图实例化到一个View中
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.listview_items, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                listItemView.textView = (TextView) convertView
                        .findViewById(R.id.title);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            Drawable img = mList.get(position).getImage();
            String title = mList.get(position).getTitle();

            // 将资源传递给ListItemView的两个域对象
            listItemView.imageView.setImageDrawable(img);
            listItemView.textView.setText(title);

            // 返回convertView对象
            return convertView;
        }
    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView imageView;
        TextView textView;
    }
}
