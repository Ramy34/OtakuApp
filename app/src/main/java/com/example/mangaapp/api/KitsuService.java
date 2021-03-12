package com.example.mangaapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Interface in charge of the interaction with the API

public interface KitsuService {

    @GET("manga")
    Call<MangaResponse> getMangaList(@Query("page[limit]") int limit, @Query("page[offset]") int offset);

    @GET("manga?filter")
    Call<MangaResponse> getSearchManga(@Query("filter[text]") String text, @Query("page[limit]") int limit, @Query("page[offset]") int offset);

    @GET("anime")
    Call<AnimeResponse> getAnimeList(@Query("page[limit]") int limit, @Query("page[offset]") int offset);

    @GET("anime?filter")
    Call<AnimeResponse> getSearchAnime(@Query("filter[text]") String text, @Query("page[limit]") int limit, @Query("page[offset]") int offset);
}
