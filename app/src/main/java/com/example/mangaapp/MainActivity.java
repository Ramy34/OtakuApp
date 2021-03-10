package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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
    private RecyclerView recyclerView;
    private MangaListAdapter mangaListAdapter;
    private boolean aptoParaCargar = true;
    private int offset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        mangaListAdapter = new MangaListAdapter(this);
        recyclerView.setAdapter(mangaListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(getString(R.string.TAG), " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create()) //Convertimos lo que nos devuelva en un gson
                .build();

        aptoParaCargar = true;
        obtenerDatos(offset);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void obtenerDatos(int offset) {
        KitsuService service = retrofit.create(KitsuService.class);
        Call<MangaResponse> mangaResponseCall = service.obtenerListaManga(20, offset);
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    MangaResponse mangaResponse = response.body();
                    ArrayList<Manga> mangaList = mangaResponse.getData();
                    mangaListAdapter.addMangaList(mangaList);

                    for (int i = 0; i < mangaList.size(); i++){
                        Attributes attributes = mangaList.get(i).getAttributes();
                        Log.e(getResources().getString(R.string.TAG), " Name: " + attributes.getCanonicalTitle());
                    }

                }else{
                    Log.e(getResources().getString(R.string.TAG), " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(getResources().getString(R.string.TAG), " onFalure: " + t.getMessage());
            }
        });
    }
}