package com.ruangguru.trivia.testapplication.di.component;

import android.content.Context;

import com.ruangguru.trivia.testapplication.data.service.APIService;
import com.ruangguru.trivia.testapplication.di.module.AppModule;
import com.ruangguru.trivia.testapplication.di.module.NetworkModule;
import com.ruangguru.trivia.testapplication.di.module.RetrofitModule;
import com.ruangguru.trivia.testapplication.di.module.RxModule;
import com.ruangguru.trivia.testapplication.di.scope.AppScope;
import com.ruangguru.trivia.testapplication.rx.RxSchedulers;

import dagger.Component;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@AppScope
@Component(modules = {NetworkModule.class, AppModule.class, RxModule.class, RetrofitModule.class})
public interface AppComponent {
    Context getAppContext();
    RxSchedulers rxSchedulers();
    APIService apiService();
}
