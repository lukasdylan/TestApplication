package com.ruangguru.trivia.testapplication.di.module;

import com.ruangguru.trivia.testapplication.rx.RxSchedulerContract;
import com.ruangguru.trivia.testapplication.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */
@Module
public class RxModule {
    @Provides
    RxSchedulers provideRxSchedulers() {
        return new RxSchedulerContract();
    }
}
