package com.example.gao.letsv.MainViews.Fragment1Code;

/**
 * Created by gangchang on 2018/6/13.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.ui.ContextHelper;
import com.alexvasilkov.android.commons.ui.Views;
import com.example.gao.letsv.MainViews.MainActivity;
import com.example.gao.letsv.R;


import java.util.Arrays;

public class PaintingsAdapter extends ItemsAdapter<Painting, PaintingsAdapter.ViewHolder>
        implements View.OnClickListener {

    public PaintingsAdapter(Context context) {
        setItemsList(Arrays.asList(Painting.getAllPaintings(context.getResources())));
    }

    @Override
    protected ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(parent);
        holder.image.setOnClickListener(this);
        return holder;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        final Painting item = getItem(position);

        holder.image.setTag(R.id.homepage_f1_listitem_image, item);
        GlideHelper.loadPaintingImage(holder.image, item);
        holder.title.setText(item.getTitle());
    }

    @Override
    public void onClick(View view) {
        final Painting item = (Painting) view.getTag(R.id.homepage_f1_listitem_image);
        final Activity activity = ContextHelper.asActivity(view.getContext());
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).openDetails(view, item);
        }
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView image;
        final TextView title;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.fragment_homepage_1_listitem));
            image = Views.find(itemView, R.id.homepage_f1_listitem_image);
            title = Views.find(itemView, R.id.homepage_f1_listitem_title);
        }
    }

}