package com.example.mangaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.R;

public class MainActivity3 extends AppCompatActivity {

    ImageView cover; //They are defined to handle graphic components

    //Constant to handle case of some error when receiving the data
    final static int defaulValues = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //We obtain the data of the manga that we want to show
        int id = getIntent().getIntExtra(getString(R.string.Id), defaulValues);

        //We make the variable-component relationship
        cover = findViewById(R.id.cover);

        //Load de image
        Glide.with(this)
                .load(getString(R.string.image_url) + id + getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cover);

    }
}