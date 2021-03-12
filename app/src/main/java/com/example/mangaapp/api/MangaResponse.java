package com.example.mangaapp.api;

import com.example.mangaapp.models.anime.Anime;
import com.example.mangaapp.models.manga.Manga;
import java.util.ArrayList;

public class MangaResponse {
    private ArrayList<Manga> data;

    public ArrayList<Manga> getData() {
        return data;
    }

    public void setData(ArrayList<Manga> data) {
        this.data = data;
    }

}
