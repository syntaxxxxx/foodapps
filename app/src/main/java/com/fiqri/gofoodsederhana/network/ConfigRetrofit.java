package com.fiqri.gofoodsederhana.network;

import com.fiqri.gofoodsederhana.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    public static Retrofit setInit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    public static ApiService getInstancee(){
        return setInit().create(ApiService.class);
    }
}
