package com.example.mangaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mangaapp.models.Attributes;
import com.example.mangaapp.models.Manga;
import com.example.mangaapp.models.PosterImage;

import java.util.ArrayList;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> {
    private ArrayList<Manga> dataset;
    private Context context;

    public MangaListAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Manga manga = dataset.get(position);
        holder.mangaName.setText(manga.getAttributes().getCanonicalTitle());

        Glide.with(context)
                .load(context.getResources().getString(R.string.image_url) + manga.getId() + context.getResources().getString(R.string.jpg))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mangaCover);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addMangaList(ArrayList<Manga> mangaList) {
        dataset.addAll(mangaList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mangaCover;
        private TextView mangaName;

        public ViewHolder(View itemView){
            super(itemView);

            mangaCover = itemView.findViewById(R.id.mangaCover);
            mangaName = itemView.findViewById(R.id.mangaName);
        }
    }
}
