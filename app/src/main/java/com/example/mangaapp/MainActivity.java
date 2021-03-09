package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mangaapp.api.KitsuService;
import com.example.mangaapp.api.MangaResponse;
import com.example.mangaapp.models.Attributes;
import com.example.mangaapp.models.Manga;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create()) //Convertimos lo que nos devuelva en un gson
                .build();

        obtenerDatos();
    }

    private void obtenerDatos() {
        KitsuService service = retrofit.create(KitsuService.class);
        Call<MangaResponse> mangaResponseCall = service.obtenerListaManga();
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                if(response.isSuccessful()){
                    MangaResponse mangaResponse = response.body();
                    ArrayList<Manga> listaManga = mangaResponse.getData();

                    for (int i = 0; i < listaManga.size(); i++){
                        Attributes attributes = listaManga.get(i).getAttributes();
                        Log.e(getResources().getString(R.string.TAG), " Name: " + attributes.getCanonicalTitle());
                    }

                }else{
                    Log.e(getResources().getString(R.string.TAG), " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                Log.e(getResources().getString(R.string.TAG), " onFalure: " + t.getMessage());
            }
        });
    }


}