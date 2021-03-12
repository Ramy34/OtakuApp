package com.example.mangaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.R;

public class MainActivity2 extends AppCompatActivity {

    //They are defined to handle graphic components
    ImageButton poster;
    TextView tvCanonicalTitle;
    TextView tvSynopsis;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvStatus;
    TextView tvChapterCount;
    TextView tvVolumeCount;
    TextView tvSerialization;

    //Constant to handle case of some error when receiving the data
    final static int defaultValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //We obtain the data of the manga that we want to show
        int id = getIntent().getIntExtra(getString(R.string.Id), defaultValue);
        String synopsis = getIntent().getStringExtra(getString(R.string.Synopsis));
        String canonicalTitles = getIntent().getStringExtra(getString(R.string.CanonicalTitle));
        String startDate = validation(getIntent().getStringExtra(getString(R.string.StartDate)));
        String endtDate = validation(getIntent().getStringExtra(getString(R.string.EndDate)));
        String status = getIntent().getStringExtra(getString(R.string.Status));
        int chapterCount = getIntent().getIntExtra(getString(R.string.ChapterCount), defaultValue);
        int volumeCount = getIntent().getIntExtra(getString(R.string.VolumeCount), defaultValue);
        String serialization = getIntent().getStringExtra(getString(R.string.Serialization));

        //We make the variable-component relationship
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

        //Event when poster is pressed
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We send the id of the manga to a new activity
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra(getString(R.string.Id), id);
                startActivity(intent);
            }
        });
    }

    //In some cases the start date and the end date are null values so we change the value to unknown.
    private String validation(String string) {
        if(string == null){
            string = getString(R.string.Unknown);
        }
        return string;
    }

    //Load all the information we get from the selected manga and display it.
    @SuppressLint("SetTextI18n")
    private void load(int id, String synopsis, String canonicalTitles, String startDate, String endtDate, String status, int chapterCount, int volumeCount, String serialization) {
        tvCanonicalTitle.setText(canonicalTitles);
        tvSynopsis.setText(synopsis);
        tvStartDate.setText(getString(R.string.tvStartDate) + startDate);
        tvEndDate.setText(getString(R.string.tvEndDate) + endtDate);
        tvStatus.setText(getString(R.string.tvStatus) + status);
        tvSerialization.setText(getString(R.string.tvSerialization) + serialization);
        tvChapterCount.setText( getString(R.string.tvChapterCount) + chapterCount);
        tvVolumeCount.setText( getString(R.string.tvVolumeCount) + volumeCount);

        Glide.with(this)
                .load(getString(R.string.image_url) + id + getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
    }


}