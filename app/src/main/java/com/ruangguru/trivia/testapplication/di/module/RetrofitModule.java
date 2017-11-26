package com.ruangguru.trivia.testapplication.di.module;

import com.ruangguru.trivia.testapplication.data.service.APIService;
import com.ruangguru.trivia.testapplication.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@Module
public class RetrofitModule {
    @Provides
    @AppScope
    APIService provideAPIService(OkHttpClient client, GsonConverterFactory gson, RxJava2CallAdapterFactory rxAdapter){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .build();

        return  retrofit.create(APIService.class);
    }
}