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
    TextView tvExtra1;
    TextView tvExtra2;
    TextView tvSerialization;

    //Constant to handle case of some error when receiving the data
    final static int defaultValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //We make the variable-component relationship
        poster = findViewById(R.id.poster);
        tvSynopsis = findViewById(R.id.tvSynopsis);
        tvCanonicalTitle = findViewById(R.id.tvCanonicalTitle);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvStatus = findViewById(R.id.tvStatus);
        tvExtra1 = findViewById(R.id.tvExtra1);
        tvExtra2 = findViewById(R.id.tvExtra2);
        tvSerialization = findViewById(R.id.tvSerialization);

        //We obtain the data of the manga that we want to show
        int id = getIntent().getIntExtra(getString(R.string.Id), defaultValue);
        String type = getIntent().getStringExtra(getString(R.string.Type));
        String synopsis = getIntent().getStringExtra(getString(R.string.Synopsis));
        String canonicalTitles = getIntent().getStringExtra(getString(R.string.CanonicalTitle));
        String startDate = validation(getIntent().getStringExtra(getString(R.string.StartDate)));
        String endtDate = validation(getIntent().getStringExtra(getString(R.string.EndDate)));
        String status = getIntent().getStringExtra(getString(R.string.Status));
        int chapterCount = getIntent().getIntExtra(getString(R.string.ChapterCount), defaultValue);
        int volumeCount = getIntent().getIntExtra(getString(R.string.VolumeCount), defaultValue);
        String serialization = getIntent().getStringExtra(getString(R.string.Serialization));
        int episodeCount =getIntent().getIntExtra(getString(R.string.EpisodeCount), defaultValue);
        String youtubeVideoId = getIntent().getStringExtra(getString(R.string.YoutubeVideoId));

        load(id, synopsis, canonicalTitles, startDate, endtDate, status, chapterCount, volumeCount, serialization, episodeCount, youtubeVideoId, type);

        //Event when poster is pressed
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We send the id of the manga to a new activity
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra(getString(R.string.Id), id);
                intent.putExtra(getString(R.string.Type), type);
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
    private void load(int id, String synopsis, String canonicalTitles, String startDate, String endtDate, String status, int chapterCount, int volumeCount, String serialization, int episodeCount, String youtubeVideoID, String type) {
        String url_img;
        tvCanonicalTitle.setText(canonicalTitles);
        tvSynopsis.setText(synopsis);
        tvStartDate.setText(getString(R.string.tvStartDate) + startDate);
        tvEndDate.setText(getString(R.string.tvEndDate) + endtDate);
        tvStatus.setText(getString(R.string.tvStatus) + status);

        if(type.equals(getString(R.string.Manga).toLowerCase())){
            tvExtra1.setText( getString(R.string.tvChapterCount) + chapterCount);
            tvExtra2.setText( getString(R.string.tvVolumeCount) + volumeCount);
            tvSerialization.setVisibility(View.VISIBLE);
            tvSerialization.setText(getString(R.string.tvSerialization) + serialization);
            url_img = getString(R.string.image_url_manga);

        }else{
            tvExtra1.setText( getString(R.string.tvEpisodeCount) + episodeCount);
            tvExtra2.setText( getString(R.string.tvYoutubeID) + youtubeVideoID);
            tvSerialization.setVisibility(View.GONE);
            url_img = getString(R.string.image_url_anime);
        }

        Glide.with(this)
                .load(url_img + id + getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);

    }
}