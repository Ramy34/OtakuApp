package com.example.mangaapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KitsuService {

    @GET("manga")
    Call<MangaResponse> obtenerListaManga(@Query("page[limit]") int limit, @Query("page[offset]") int offset);
}
