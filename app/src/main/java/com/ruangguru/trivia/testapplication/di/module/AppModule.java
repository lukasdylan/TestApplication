package com.ruangguru.trivia.testapplication.di.module;

import android.content.Context;

import com.ruangguru.trivia.testapplication.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context aContext) {
        this.context = aContext.getApplicationContext();
    }

    @AppScope
    @Provides
    Context provideAppContext() {
        return context;
    }

//    @Singleton
//    @Provides
//    @Inject
//    SharedPreferences provideSharedPreferences() {
//        return context.getSharedPreferences("Preference",Context.MODE_PRIVATE);
//    }
}
