package com.ruangguru.trivia.testapplication.ui.splash_screen.sub_module;

import com.ruangguru.trivia.testapplication.rx.RxSchedulers;
import com.ruangguru.trivia.testapplication.ui.splash_screen.SplashActivity;
import com.ruangguru.trivia.testapplication.ui.splash_screen.SplashModel;
import com.ruangguru.trivia.testapplication.ui.splash_screen.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@Module
public class SplashModule {
    private final SplashActivity splashActivity;

    public SplashModule(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
    }

    @SplashScope
    @Provides
    SplashActivity provideActivity() {
        return splashActivity;
    }

    @SplashScope
    @Provides
    SplashPresenter providePresenter(RxSchedulers schedulers, SplashModel model) {
        CompositeDisposable disposable = new CompositeDisposable();
        return new SplashPresenter(model, schedulers, disposable);
    }

    @SplashScope
    @Provides
    SplashModel provideSplashModel() {
        return new SplashModel();
    }
}
