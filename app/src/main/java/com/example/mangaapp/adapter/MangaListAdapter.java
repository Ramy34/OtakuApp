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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import com.example.mangaapp.activity.MainActivity2;
import com.example.mangaapp.models.Manga;

import androidx.annotation.NonNull;
import com.example.mangaapp.R;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> implements Filterable {
    private ArrayList<Manga> mangaList;
    private ArrayList<Manga> filteredMangaList;
    private Context context;

    public MangaListAdapter(Context context) {
        this.context = context;
        mangaList = new ArrayList<>();
        this.filteredMangaList = this.mangaList;
    }

    @NonNull
    @Override
    public MangaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaListAdapter.ViewHolder holder, int position) {
        holder.mangaName.setText(filteredMangaList.get(position).getAttributes().getCanonicalTitle());
        Glide.with(context)
                .load(context.getResources().getString(R.string.image_url) + filteredMangaList.get(position).getId() + context.getResources().getString(R.string.jpg_large))
                .centerCrop()
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mangaCover;
        private TextView mangaName;

        public ViewHolder(View itemView){
            super(itemView);
            mangaCover = itemView.findViewById(R.id.mangaCover);
            mangaName = itemView.findViewById(R.id.mangaName);
        }

        public void setOnClickListener() {
            mangaCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Manga manga = filteredMangaList.get(getAdapterPosition());
            Toast.makeText(context, manga.getAttributes().getCanonicalTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(itemView.getContext(), MainActivity2.class);

            int id = manga.getId();
            String synopsis = manga.getAttributes().getSynopsis();
            String canonicalTitles = manga.getAttributes().getCanonicalTitle();
            String startDate = manga.getAttributes().getStartDate();
            String endtDate = manga.getAttributes().getEndDate();
            String status = manga.getAttributes().getStatus();
            int chapterCount = manga.getAttributes().getChapterCount();
            int volumeCount = manga.getAttributes().getVolumeCount();
            String serialization = manga.getAttributes().getSerialization();

            intent.putExtra("Id", id);
            intent.putExtra("Synopsis", synopsis);
            intent.putExtra("CanonicalTitle", canonicalTitles);
            intent.putExtra("StartDate", startDate);
            intent.putExtra("EndDate", endtDate);
            intent.putExtra("Status", status);
            intent.putExtra("ChapterCount", chapterCount);
            intent.putExtra("VolumeCount", volumeCount);
            intent.putExtra("Serialization", serialization);
            context.startActivity(intent);
        }
    }
}
