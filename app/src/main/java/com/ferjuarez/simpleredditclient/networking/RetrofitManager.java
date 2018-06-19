package com.ferjuarez.simpleredditclient.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ferjuarez on 3/15/17.
 */
public class RetrofitManager {

    private static RetrofitManager mInstance = null;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        if(mInstance != null){
            return mInstance;
        } else {
            return new RetrofitManager();
        }
    }

    private RetrofitManager() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String BASE_URL = "https://www.reddit.com";
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public RedditService getRedditService() {
        return mRetrofit.create(RedditService.class);
    }

}