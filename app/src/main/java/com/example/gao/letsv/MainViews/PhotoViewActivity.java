package com.example.gao.letsv.MainViews;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.gao.letsv.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;


public class PhotoViewActivity extends AppCompatActivity {

    PhotoView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        iv = findViewById(R.id.photoview);
        //PhotoViewAttacher attacher = new PhotoViewAttacher(iv);
        Picasso.get().load("http://47.93.25.104:5000/getimage").into(iv);
    }

}
