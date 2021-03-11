package com.example.mangaapp.api;

import com.example.mangaapp.models.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KitsuService {

    @GET("manga")
    Call<MangaResponse> getMangaList(@Query("page[limit]") int limit, @Query("page[offset]") int offset);

    @GET("manga?filter")
    Call<MangaResponse> getSearch(@Query("filter[text]") String text, @Query("page[limit]") int limit, @Query("page[offset]") int offset);
}
