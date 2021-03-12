package com.example.mangaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mangaapp.R;

public class MainActivity4 extends AppCompatActivity {

    ImageButton ibManga;
    ImageButton ibAnime;
    ImageButton ibFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ibManga = findViewById(R.id.ivManga);
        ibAnime = findViewById(R.id.ivAnime);
        ibFavorites = findViewById(R.id.ivFavorite);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity4.this, MainActivity.class);
        switch (view.getId()){
            case R.id.ivManga:
                intent.putExtra(getString(R.string.Option), 0);
                break;
            case R.id.ivAnime:
                intent.putExtra(getString(R.string.Option), 1);
                break;
            case R.id.ivFavorite:
                intent.putExtra(getString(R.string.Option), 2);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}