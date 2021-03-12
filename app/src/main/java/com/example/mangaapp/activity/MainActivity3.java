package com.example.mangaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.R;

public class MainActivity3 extends AppCompatActivity {

    ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        cover = findViewById(R.id.cover);

        int id = getIntent().getIntExtra("Id", 0);

        Glide.with(this)
                .load(getString(R.string.image_url) + id + getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cover);

    }
}