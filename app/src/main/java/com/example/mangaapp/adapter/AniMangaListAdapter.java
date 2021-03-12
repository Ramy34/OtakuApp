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
import com.example.mangaapp.models.AniManga;

import java.util.ArrayList;

public class AniMangaListAdapter extends RecyclerView.Adapter<AniMangaListAdapter.ViewHolder> implements Filterable {

    private int option;
    private final ArrayList<AniManga> aniMangaList;
    private ArrayList<AniManga> filteredAniMangaList;
    private final Context context;
    private String url_img;

    //Constructor
    public AniMangaListAdapter(Context context, int option) {
        this.context = context;
        this.option = option;
        aniMangaList = new ArrayList<>();
        this.filteredAniMangaList = this.aniMangaList;
    }

    @NonNull
    @Override
    public AniMangaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //We relate the view with item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new AniMangaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AniMangaListAdapter.ViewHolder holder, int position) {
        //Load the information
        holder.name.setText(filteredAniMangaList.get(position).getAttributes().getCanonicalTitle());
        if(option == 0){
            url_img = context.getResources().getString(R.string.image_url_manga);
        }else {
            url_img = context.getResources().getString(R.string.image_url_anime);
        }
        Glide.with(context)
                .load( url_img+ filteredAniMangaList.get(position).getId() + context.getResources().getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cover);
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return filteredAniMangaList.size();
    }

    public void addList(ArrayList<AniManga> aniMangaList) {
        filteredAniMangaList.addAll(aniMangaList);
        notifyDataSetChanged();
    }

    public void resetList(){
        filteredAniMangaList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        //Search method
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                String searchString = charSequence.toString();
                if(searchString.isEmpty()){
                    filteredAniMangaList = aniMangaList;
                }else{
                    ArrayList<AniManga> tempFilteredList = new ArrayList<>();
                    for (AniManga aniManga : aniMangaList){
                        if(aniManga.getAttributes().getCanonicalTitle().toLowerCase().contains(searchString)){
                            tempFilteredList.add(aniManga);
                        }
                    }
                    filteredAniMangaList = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAniMangaList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredAniMangaList = (ArrayList<AniManga>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView cover;
        private final TextView name;

        public ViewHolder(View itemView){
            super(itemView);
            cover = itemView.findViewById(R.id.principalCover);
            name = itemView.findViewById(R.id.principalName);
        }

        public void setOnClickListener() {
            cover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AniManga aniManga = filteredAniMangaList.get(getAdapterPosition());
            Intent intent = new Intent(itemView.getContext(), MainActivity2.class);

            int id = aniManga.getId();
            String type = aniManga.getType();
            String synopsis = aniManga.getAttributes().getSynopsis();
            String canonicalTitles = aniManga.getAttributes().getCanonicalTitle();
            String startDate = aniManga.getAttributes().getStartDate();
            String endtDate = aniManga.getAttributes().getEndDate();
            String status = aniManga.getAttributes().getStatus();
            int chapterCount = aniManga.getAttributes().getChapterCount();
            int volumeCount = aniManga.getAttributes().getVolumeCount();
            String serialization = aniManga.getAttributes().getSerialization();
            int episodeCount = aniManga.getAttributes().getEpisodeCount();
            String youtubeVideoId = aniManga.getAttributes().getYoutubeVideoId();

            intent.putExtra(context.getString(R.string.Id), id);
            intent.putExtra(context.getString(R.string.Type), type);
            intent.putExtra(context.getString(R.string.Synopsis), synopsis);
            intent.putExtra(context.getString(R.string.CanonicalTitle), canonicalTitles);
            intent.putExtra(context.getString(R.string.StartDate), startDate);
            intent.putExtra(context.getString(R.string.EndDate), endtDate);
            intent.putExtra(context.getString(R.string.Status), status);
            intent.putExtra(context.getString(R.string.ChapterCount), chapterCount);
            intent.putExtra(context.getString(R.string.VolumeCount), volumeCount);
            intent.putExtra(context.getString(R.string.Serialization), serialization);
            intent.putExtra(context.getString(R.string.EpisodeCount), episodeCount);
            intent.putExtra(context.getString(R.string.YoutubeVideoId), youtubeVideoId);
            context.startActivity(intent);
        }
    }

}
