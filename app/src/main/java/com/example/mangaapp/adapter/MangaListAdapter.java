package com.example.mangaapp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import com.example.mangaapp.activity.MainActivity2;
import com.example.mangaapp.models.manga.Manga;

import androidx.annotation.NonNull;
import com.example.mangaapp.R;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> implements Filterable {
    private final ArrayList<Manga> mangaList;
    private ArrayList<Manga> filteredMangaList;
    private final Context context;

    //Constructor
    public MangaListAdapter(Context context) {
        this.context = context;
        mangaList = new ArrayList<>();
        this.filteredMangaList = this.mangaList;
    }

    @NonNull
    @Override
    public MangaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //We relate the view with item_manga
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaListAdapter.ViewHolder holder, int position) {
        //Load the information
        holder.mangaName.setText(filteredMangaList.get(position).getAttributes().getCanonicalTitle());
        Glide.with(context)
                .load(context.getResources().getString(R.string.image_url_manga) + filteredMangaList.get(position).getId() + context.getResources().getString(R.string.jpg_large))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mangaCover);
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return filteredMangaList.size();
    }

    @Override
    public Filter getFilter() {
        //Search method
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                String searchString = charSequence.toString();
                if(searchString.isEmpty()){
                    filteredMangaList = mangaList;
                }else{
                    ArrayList<Manga> tempFilteredList = new ArrayList<>();
                    for (Manga manga : mangaList){
                        if(manga.getAttributes().getCanonicalTitle().toLowerCase().contains(searchString)){
                            tempFilteredList.add(manga);
                        }
                    }
                    filteredMangaList = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMangaList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMangaList = (ArrayList<Manga>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void addMangaList(ArrayList<Manga> mangaList) {
        filteredMangaList.addAll(mangaList);
        notifyDataSetChanged();
    }

    public void resetMangaList(){
        filteredMangaList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView mangaCover;
        private final TextView mangaName;

        public ViewHolder(View itemView){
            super(itemView);
            mangaCover = itemView.findViewById(R.id.principalCover);
            mangaName = itemView.findViewById(R.id.principalName);
        }

        public void setOnClickListener() {
            mangaCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Manga manga = filteredMangaList.get(getAdapterPosition());
            Intent intent = new Intent(itemView.getContext(), MainActivity2.class);

            int id = manga.getId();
            String type = manga.getType();
            String synopsis = manga.getAttributes().getSynopsis();
            String canonicalTitles = manga.getAttributes().getCanonicalTitle();
            String startDate = manga.getAttributes().getStartDate();
            String endtDate = manga.getAttributes().getEndDate();
            String status = manga.getAttributes().getStatus();
            int chapterCount = manga.getAttributes().getChapterCount();
            int volumeCount = manga.getAttributes().getVolumeCount();
            String serialization = manga.getAttributes().getSerialization();


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
            context.startActivity(intent);
        }
    }
}
