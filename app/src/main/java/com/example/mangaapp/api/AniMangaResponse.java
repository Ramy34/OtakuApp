package com.example.mangaapp.api;

import com.example.mangaapp.models.AniManga;

import java.util.ArrayList;

public class AniMangaResponse {
    private ArrayList<AniManga> data;

    public ArrayList<AniManga> getData() {
        return data;
    }

    public void setData(ArrayList<AniManga> data) {
        this.data = data;
    }
}
