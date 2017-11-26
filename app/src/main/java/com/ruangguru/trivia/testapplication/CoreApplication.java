package com.ruangguru.trivia.testapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.ruangguru.trivia.testapplication.di.component.AppComponent;
import com.ruangguru.trivia.testapplication.di.component.DaggerAppComponent;
import com.ruangguru.trivia.testapplication.di.module.AppModule;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class CoreApplication extends Application{

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        FlowManager.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
