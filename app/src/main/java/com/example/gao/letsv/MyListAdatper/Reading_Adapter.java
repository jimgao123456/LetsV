package com.example.gao.letsv.MyListAdatper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.ui.ContextHelper;
import com.alexvasilkov.android.commons.ui.Views;
import com.example.gao.letsv.GlideHelper;
import com.example.gao.letsv.R;
import com.example.gao.letsv.reading_FoldableListActivity;
import com.example.gao.letsv.reading_UnfoldableDetailsActivity;
import com.example.gao.letsv.reading_picture;

import java.util.Arrays;

/**
 * Created by dk150 on 2018/6/8.
 */

public class Reading_Adapter extends ItemsAdapter<reading_picture,Reading_Adapter.ViewHolder>
implements View.OnClickListener{

    public Reading_Adapter(Context context){
        setItemsList(Arrays.asList(reading_picture.getAllPicture(context.getResources())));
    }

    protected ViewHolder onCreateHolder(ViewGroup parent, int viewType){
        final ViewHolder holder = new ViewHolder(parent);
        holder.image.setOnClickListener(this);
        return holder;
    }

    protected void onBindHolder(ViewHolder holder, int position) {
        final reading_picture item = getItem(position);

        holder.image.setTag(R.id.list_item_image, item);
        GlideHelper.loadPicImage(holder.image, item);
        holder.title.setText(item.getPicTitle());
    }

    public void onClick(View view) {
        final reading_picture item = (reading_picture) view.getTag(R.id.list_item_image);
        final Activity activity = ContextHelper.asActivity(view.getContext());

        if (activity instanceof reading_UnfoldableDetailsActivity) {
            ((reading_UnfoldableDetailsActivity) activity).openDetails(view, item);
        } else if (activity instanceof reading_FoldableListActivity) {
            Toast.makeText(activity, item.getPicTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView image;
        final TextView title;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.listview_items));
            image = Views.find(itemView, R.id.list_item_image);
            title = Views.find(itemView, R.id.list_item_title);
        }
    }
}
