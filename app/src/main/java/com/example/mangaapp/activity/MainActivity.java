package com.example.mangaapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
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

    private int flag = 0;

    private boolean aptoParaCargar = true;
    private String query;
    private int offset = 0;
    private int offsetSearch = 0;


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
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                            aptoParaCargar = false;
                            if(flag == 0){
                                offset += 20;
                                getMangaList(offset);
                            }else{
                                offsetSearch += 20;
                                search(query);
                            }
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
        Call<MangaResponse> mangaResponseCall = kitsuService.getMangaList(20, offset);
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    mangaResponse = response.body();
                    mangaList = mangaResponse.getData();
                    mangaListAdapter.addMangaList(mangaList);
                    flag = 0;
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
        searchView.setQueryHint(getResources().getString(R.string.Search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mangaListAdapter.resetMangaList();
                offsetSearch = 0;
                search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mangaListAdapter.getFilter().filter(newText);
                flag = 0;
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String queryLocal) {
        query = queryLocal;

        KitsuService kitsuService = RetrofitClient.getClient(this).create(KitsuService.class);
        Call<MangaResponse> mangaResponseCall = kitsuService.getSearch(queryLocal, 20, offsetSearch);
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()){
                    mangaResponse = response.body();
                    ArrayList<Manga> list = mangaResponse.getData();
                    mangaListAdapter.addMangaList(list);
                    flag = 1;
                }else{
                    Toast.makeText(getApplicationContext(), "onRespone in search: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                aptoParaCargar = true;
                Toast.makeText(getApplicationContext(), "onFalure in search: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}