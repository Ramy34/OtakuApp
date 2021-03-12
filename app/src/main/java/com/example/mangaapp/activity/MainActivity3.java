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
    String url_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //We obtain the data of the manga or anime that we want to show
        int id = getIntent().getIntExtra(getString(R.string.Id), defaulValues);
        String type = getIntent().getStringExtra(getString(R.string.Type));

        //We make the variable-component relationship
        cover = findViewById(R.id.cover);

        if(type.equals(getString(R.string.Manga).toLowerCase())){
            url_image = getString(R.string.image_url_manga);
        }else{
            url_image = getString(R.string.image_url_anime);
        }
        //Load de image
        Glide.with(this)
                .load( url_image+ id + getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cover);

    }
}