package com.example.mangaapp.api;

import com.example.mangaapp.models.anime.Anime;

import java.util.ArrayList;

public class AnimeResponse {
    private ArrayList<Anime> data;

    public ArrayList<Anime> getData() {
        return data;
    }

    public void setData(ArrayList<Anime> data) {
        this.data = data;
    }
}
