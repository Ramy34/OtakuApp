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
import com.example.mangaapp.adapter.AnimeListAdapter;
import com.example.mangaapp.adapter.MangaListAdapter;
import com.example.mangaapp.api.AnimeResponse;
import com.example.mangaapp.api.KitsuService;
import com.example.mangaapp.api.MangaResponse;
import com.example.mangaapp.models.anime.Anime;
import com.example.mangaapp.models.manga.Manga;
import com.example.mangaapp.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView; //It is in charge of managing the Recyclerview

    private boolean fitToLoad = true; //Takes care of loading more mangas

    private int flag = 0; //Identifies if it is charging in normal mode (0) or in search mode (1)

    private String query; //The searched word

    private int offset = 0; //Manages normal mode offset

    private int offsetSearch = 0; //Manages search mode offset

    final static int defaultValue = 0; //Constant to handle case of some error when receiving the data

    final static int limit = 20; //Constant

    //Only Manga
    private MangaListAdapter mangaListAdapter; //Is in charge of managing the Adapter

    private MangaResponse mangaResponse; //It is in charge of handling the API response

    private ArrayList<Manga> mangaList; //Stores the list of mangas

    //only Anime
    private AnimeListAdapter animeListAdapter; //Is in charge of managing the Adapter

    private AnimeResponse animeResponse; //It is in charge of handling the API response

    private ArrayList<Anime> animeList; //Stores the list of mangas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int option = getIntent().getIntExtra(getString(R.string.Option), defaultValue);
        recyclerView = findViewById(R.id.recyclerView); //We relate the variable to the container of the graphic view
        //We configure the RecyclerView
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager manager= new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        switch (option){
            case 0:
                //We initialize the adapter handler
                mangaListAdapter = new MangaListAdapter(this);
                recyclerView.setAdapter(mangaListAdapter);

                break;
            case 1:
                animeListAdapter = new AnimeListAdapter(this);
                recyclerView.setAdapter(animeListAdapter);
                break;
            case 2:
                Toast.makeText(this, "Estás en la lista de favoritos", Toast.LENGTH_SHORT).show();
                break;
        }

        //This method will take care of showing new elements every time we reach the end of the list
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = manager.getChildCount();
                    int totalItemCount = manager.getItemCount();
                    int pastVisibleItems = manager.findFirstVisibleItemPosition();
                    if (fitToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            fitToLoad = false;
                            if(flag == 0){
                                offset += 20;
                                getList(offset, option);
                            }else{
                                offsetSearch += 20;
                                search(query);
                            }
                        }
                    }
                }
            }
        });
        fitToLoad = true;
        getList(offset, option);


    }

    //This method will be in charge of making the request to the API and will show the results
    private void getList(int offset, int option){
        KitsuService kitsuService = RetrofitClient.getClient(this).create(KitsuService.class);
        switch (option){
            case 0: //Manga
                Call<MangaResponse> mangaResponseCall = kitsuService.getMangaList(limit, offset);
                mangaResponseCall.enqueue(new Callback<MangaResponse>() {
                    @Override
                    public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                        fitToLoad = true;
                        if(response.isSuccessful()){
                            mangaResponse = response.body();
                            mangaList = mangaResponse.getData();
                            mangaListAdapter.addMangaList(mangaList);
                            flag = 0;
                        }else{
                            Toast.makeText(getApplicationContext(), getString(R.string.onRespone) + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MangaResponse> call, Throwable t) {
                        fitToLoad = true;
                        Toast.makeText(getApplicationContext(), getString(R.string.onFailure) + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1: //Anime
                Call<AnimeResponse> animeResponseCall = kitsuService.getAnimeList(limit, offset);
                animeResponseCall.enqueue(new Callback<AnimeResponse>() {
                    @Override
                    public void onResponse(Call<AnimeResponse> call, Response<AnimeResponse> response) {
                        fitToLoad = true;
                        if(response.isSuccessful()){
                            animeResponse = response.body();
                            animeList = animeResponse.getData();
                            animeListAdapter.addAnimeList(animeList);
                            flag = 0;
                        }else{
                            Toast.makeText(getApplicationContext(), getString(R.string.onRespone) + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeResponse> call, Throwable t) {
                        fitToLoad = true;
                        Toast.makeText(getApplicationContext(), getString(R.string.onFailure) + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }

    }

    //This method will be in charge of adding the search button in the toolbar and performing the searches
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

    //This method will handle the search when the user sends the request
    private void search(String queryLocal) {
        query = queryLocal;
        KitsuService kitsuService = RetrofitClient.getClient(this).create(KitsuService.class);
        Call<MangaResponse> mangaResponseCall = kitsuService.getSearchManga(queryLocal, 20, offsetSearch);
        mangaResponseCall.enqueue(new Callback<MangaResponse>() {
            @Override
            public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                fitToLoad = true;
                if(response.isSuccessful()){
                    mangaResponse = response.body();
                    ArrayList<Manga> list = mangaResponse.getData();
                    mangaListAdapter.addMangaList(list);
                    flag = 1;
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.onRespone) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MangaResponse> call, Throwable t) {
                fitToLoad = true;
                Toast.makeText(getApplicationContext(), getString(R.string.onFailure) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}