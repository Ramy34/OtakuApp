package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.models.Manga;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ImageView poster;
    TextView tvCanonicalTitle;
    TextView tvSynopsis;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvStatus;
    TextView tvChapterCount;
    TextView tvVolumeCount;
    TextView tvSerialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        int id = getIntent().getIntExtra("Id", 0);
        String synopsis = getIntent().getStringExtra("Synopsis");
        String canonicalTitles = getIntent().getStringExtra("CanonicalTitle");
        String startDate = validation(getIntent().getStringExtra("StartDate"));
        String endtDate = validation(getIntent().getStringExtra("EndDate"));
        String status = getIntent().getStringExtra("Status");
        int chapterCount = getIntent().getIntExtra("ChapterCount", 0);
        int volumeCount = getIntent().getIntExtra("VolumeCount", 0);
        String serialization = getIntent().getStringExtra("Serialization");

        poster = findViewById(R.id.poster);
        tvSynopsis = findViewById(R.id.tvSynopsis);
        tvCanonicalTitle = findViewById(R.id.tvCanonicalTitle);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvStatus = findViewById(R.id.tvStatus);
        tvChapterCount = findViewById(R.id.tvChapterCount);
        tvVolumeCount = findViewById(R.id.tvVolumeCount);
        tvSerialization = findViewById(R.id.tvSerialization);

        load(id, synopsis, canonicalTitles, startDate, endtDate, status, chapterCount, volumeCount, serialization);

    }

    private String validation(String string) {
        if(string == null){
            string = getString(R.string.Unknown);
        }
        return string;
    }

    @SuppressLint("SetTextI18n")
    private void load(int id, String synopsis, String canonicalTitles, String startDate, String endtDate, String status, int chapterCount, int volumeCount, String serialization) {
        tvCanonicalTitle.setText(canonicalTitles);
        tvSynopsis.setText(synopsis);
        tvStartDate.setText(getString(R.string.StartDate) + startDate);
        tvEndDate.setText(getString(R.string.EndDate) + endtDate);
        tvStatus.setText(getString(R.string.Status) + status);
        tvSerialization.setText(getString(R.string.Serialization) + serialization);
        tvChapterCount.setText( getString(R.string.ChapterCount) + chapterCount);
        tvVolumeCount.setText( getString(R.string.VolumeCount) + volumeCount);

        Glide.with(this)
                .load(getString(R.string.image_url) + id + getString(R.string.jpg_large))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
    }



}