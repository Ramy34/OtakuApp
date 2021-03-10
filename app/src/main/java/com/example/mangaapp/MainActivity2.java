package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mangaapp.models.Manga;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        int id = getIntent().getIntExtra("Id", 0);
        String synopsis = getIntent().getStringExtra("Synopsis");
        String canonicalTitles = getIntent().getStringExtra("CanonicalTitles");
        String startDate = getIntent().getStringExtra("StartDate");
        String endtDate = getIntent().getStringExtra("EndDate");
        String status = getIntent().getStringExtra("Status");
        int chapterCount = getIntent().getIntExtra("ChapterCount", 0);
        int volumeCount = getIntent().getIntExtra("VolumeCount", 0);
        String serialization = getIntent().getStringExtra("Serialization");
    }
}