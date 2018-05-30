package com.example.gao.letsv.MyListAdatper;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gao.letsv.R;

import java.util.List;
import java.util.Map;

/**
 * 自定义适配器
 *
 * @author qiangzi
 */
public class MyAdapter_study_main extends BaseAdapter {
    private List<Map<String, Object>> dataList;
    private Context context;
    private int resource;
    private static final int TYPE_PHONETIC_SYMBOL = 0;
    private static final int TYPE_MEMORY_METHOD = 1;
    private static final int TYPE_OHTERS = 2;

    /**
     * 有参构造
     *
     * @param context  界面
     * @param dataList 数据
     * @param resource 列表项资源文件
     */
    public MyAdapter_study_main(Context context, List<Map<String, Object>> dataList,
                                int resource) {
        this.context = context;
        this.dataList = dataList;
        this.resource = resource;

    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int index) {

        return dataList.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_PHONETIC_SYMBOL;
//        } else if (position == 0) {
//
//            return TYPE_MEMORY_METHOD;
//        } else {
//            return TYPE_OHTERS;
//        }
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 3;
//    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        // 声明内部类
        AdapterStudyUtil util = null;
        // 中间变量
        final int flag = index;


        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
        if (view == null) {
            util = new AdapterStudyUtil();
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
            // 指向布局文件内部组件
            util.titleview = (TextView) view
                    .findViewById(R.id.myadapter_study_word_title);
            util.explainview = (TextView) view.findViewById(R.id.myadapter_study_word_textview);

            // 增加额外变量
            view.setTag(util);
        } else {
            util = (AdapterStudyUtil) view.getTag();
        }

        // 获取数据显示在各组件
        Map<String, Object> map = dataList.get(index);
        util.titleview.setText((String) map.get("titleview"));
        util.explainview.setText(Html.fromHtml((String) map.get("explainview")));

        // 删除按钮，添加点击事件
//        util.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                dataList.remove(flag);
//                notifyDataSetChanged();
//                Map<String, Object> map = dataList.get(flag);
//                String str = "已删除\n标题：" + map.get("title") + "\n内容："
//                        + map.get("content") + "\n日期：" + map.get("date");
//                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

}

/**
 * 内部类，用于辅助适配
 *
 * @author qiangzi
 */
class AdapterStudyUtil {
    TextView titleview, explainview;
}