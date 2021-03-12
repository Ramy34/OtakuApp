package com.example.mangaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.R;

public class MainActivity3 extends AppCompatActivity {

    ImageView cover; //They are defined to handle graphic components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //We make the variable-component relationship
        cover = findViewById(R.id.cover);

        String poster = getIntent().getStringExtra(getString(R.string.PosterImage));

        Glide.with(this)
                .load(poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cover);

    }
}