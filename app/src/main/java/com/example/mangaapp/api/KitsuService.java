package com.example.mangaapp.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KitsuService {

    @GET("manga")
    Call<MangaResponse> obtenerListaManga();
}
