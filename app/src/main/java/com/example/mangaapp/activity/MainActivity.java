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
import com.example.mangaapp.adapter.AniMangaListAdapter;
import com.example.mangaapp.api.AniMangaResponse;
import com.example.mangaapp.api.KitsuService;
import com.example.mangaapp.models.AniManga;
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

    private int option;

    //For all
    private AniMangaListAdapter aniMangaListAdapter;

    private AniMangaResponse aniMangaResponse;

    private ArrayList<AniManga> aniMangaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        option = getIntent().getIntExtra(getString(R.string.Option), defaultValue);
        recyclerView = findViewById(R.id.recyclerView); //We relate the variable to the container of the graphic view
        //We configure the RecyclerView
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager manager= new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        if(option != 2){
            aniMangaListAdapter = new AniMangaListAdapter(this, option);
            recyclerView.setAdapter(aniMangaListAdapter);
        }else{
            Toast.makeText(this, "Estás en la lista de favoritos", Toast.LENGTH_SHORT).show();
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
        Call<AniMangaResponse> aniMangaResponseCall;

        if(option == 0){//Manga
            aniMangaResponseCall = kitsuService.getMangaList(limit, offset);
        }else{//Anime
            aniMangaResponseCall = kitsuService.getAnimeList(limit, offset);
        }

        aniMangaResponseCall.enqueue(new Callback<AniMangaResponse>() {
            @Override
            public void onResponse(Call<AniMangaResponse> call, Response<AniMangaResponse> response) {
                fitToLoad = true;
                if(response.isSuccessful()){
                    aniMangaResponse = response.body();
                    aniMangaList = aniMangaResponse.getData();
                    aniMangaListAdapter.addList(aniMangaList);
                    flag = 0;
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.onRespone) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AniMangaResponse> call, Throwable t) {
                fitToLoad = true;
                Toast.makeText(getApplicationContext(), getString(R.string.onFailure) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                aniMangaListAdapter.resetList();
                offsetSearch = 0;
                search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                aniMangaListAdapter.getFilter().filter(newText);
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
        Call<AniMangaResponse> aniMangaResponseCall;
        if(option == 0){ //Manga
            aniMangaResponseCall  = kitsuService.getSearchManga(queryLocal, 20, offsetSearch);
        }else{//Anime
            aniMangaResponseCall = kitsuService.getSearchAnime(queryLocal, 20, offsetSearch);
        }
        aniMangaResponseCall.enqueue(new Callback<AniMangaResponse>() {
            @Override
            public void onResponse(Call<AniMangaResponse> call, Response<AniMangaResponse> response) {
                fitToLoad = true;
                if(response.isSuccessful()){
                    aniMangaResponse = response.body();
                    ArrayList<AniManga> list = aniMangaResponse.getData();
                    aniMangaListAdapter.addList(list);
                    flag = 1;
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.onRespone) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AniMangaResponse> call, Throwable t) {
                fitToLoad = true;
                Toast.makeText(getApplicationContext(), getString(R.string.onFailure) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}