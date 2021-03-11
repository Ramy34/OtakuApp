package com.example.mangaapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mangaapp.R;
import com.example.mangaapp.adapter.MangaListAdapter;
import com.example.mangaapp.api.KitsuService;
import com.example.mangaapp.api.MangaResponse;
import com.example.mangaapp.models.Manga;
import com.example.mangaapp.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MangaListAdapter mangaListAdapter;
    private MangaResponse mangaResponse;
    private ArrayList<Manga> mangaList;

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
        final GridLayoutManager manager= new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(manager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = manager.getChildCount();
                    int totalItemCount = manager.getItemCount();
                    int pastVisibleItems = manager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(getString(R.string.TAG), " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            getMangaList(offset);
                        }
                    }
                }
            }
        });

        aptoParaCargar = true;
        getMangaList(offset);
    }

    private void getMangaList(int offset) {
        KitsuService kitsuService = RetrofitClient.getClient(this).create(KitsuService.class);
        Call<MangaResponse> mangaResponseCall = kitsuService.obtenerListaManga(20, offset);
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    mangaResponse = response.body();
                    mangaList = mangaResponse.getData();
                    mangaListAdapter.addMangaList(mangaList);

                    Log.i(getString(R.string.TAG), "El valor del array es: " + mangaListAdapter.getItemCount());

                }else{
                    Toast.makeText(getApplicationContext(), "onRespone: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                aptoParaCargar = true;
                Toast.makeText(getApplicationContext(), "onFalure " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = null;
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mangaListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}