package com.example.mangaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.R;
import com.example.mangaapp.activity.MainActivity2;
import com.example.mangaapp.models.anime.Anime;

import java.util.ArrayList;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.ViewHolder> implements Filterable {

    private final ArrayList<Anime> animeList;
    private ArrayList<Anime> filteredAnimeList;
    private final Context context;

    //Constructor
    public AnimeListAdapter(Context context) {
        this.context = context;
        animeList = new ArrayList<>();
        this.filteredAnimeList = this.animeList;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public AnimeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //We relate the view with item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new AnimeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListAdapter.ViewHolder holder, int position) {
        //Load the information
        holder.animeName.setText(filteredAnimeList.get(position).getAttributes().getCanonicalTitle());
        Glide.with(context)
                .load(context.getResources().getString(R.string.image_url_anime) + filteredAnimeList.get(position).getId() + context.getResources().getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.animeCover);
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return filteredAnimeList.size();
    }

    public void addAnimeList(ArrayList<Anime> animeList) {
        filteredAnimeList.addAll(animeList);
        notifyDataSetChanged();
    }

    public void resetAnimeList(){
        filteredAnimeList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView animeCover;
        private final TextView animeName;

        public ViewHolder(View itemView){
            super(itemView);
            animeCover = itemView.findViewById(R.id.principalCover);
            animeName = itemView.findViewById(R.id.principalName);
        }

        public void setOnClickListener() {
            animeCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Anime anime = filteredAnimeList.get(getAdapterPosition());
            Intent intent = new Intent(itemView.getContext(), MainActivity2.class);

            int id = anime.getId();
            String type = anime.getType();
            String synopsis = anime.getAttributes().getSynopsis();
            String canonicalTitles = anime.getAttributes().getCanonicalTitle();
            String startDate = anime.getAttributes().getStartDate();
            String endtDate = anime.getAttributes().getEndDate();
            String status = anime.getAttributes().getStatus();
            int episodeCount = anime.getAttributes().getEpisodeCount();
            String youtubeVideoId = anime.getAttributes().getYoutubeVideoId();

            intent.putExtra(context.getString(R.string.Id), id);
            intent.putExtra(context.getString(R.string.Type), type);
            intent.putExtra(context.getString(R.string.Synopsis), synopsis);
            intent.putExtra(context.getString(R.string.CanonicalTitle), canonicalTitles);
            intent.putExtra(context.getString(R.string.StartDate), startDate);
            intent.putExtra(context.getString(R.string.EndDate), endtDate);
            intent.putExtra(context.getString(R.string.Status), status);
            intent.putExtra(context.getString(R.string.EpisodeCount), episodeCount);
            intent.putExtra(context.getString(R.string.YoutubeVideoId), youtubeVideoId);
            context.startActivity(intent);
        }
    }
}
