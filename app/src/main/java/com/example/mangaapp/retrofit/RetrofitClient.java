package com.example.mangaapp.retrofit;

import android.content.Context;

import com.example.mangaapp.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.baseURL))
                    .addConverterFactory(GsonConverterFactory.create()) //Convertimos lo que nos devuelva en un gson
                    .build();
        }
        return retrofit;
    }
}
