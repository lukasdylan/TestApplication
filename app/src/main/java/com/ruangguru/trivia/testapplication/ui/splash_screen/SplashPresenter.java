package com.ruangguru.trivia.testapplication.ui.splash_screen;

import com.ruangguru.trivia.testapplication.rx.RxSchedulers;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class SplashPresenter implements SplashInterface.Presenter{

    private final SplashModel model;
    private final RxSchedulers schedulersContract;
    private final CompositeDisposable compositeDisposable;

    public SplashPresenter(SplashModel model, RxSchedulers schedulers, CompositeDisposable disposable) {
        this.model = model;
        this.schedulersContract = schedulers;
        this.compositeDisposable = disposable;
    }


    @Override
    public void initCategoryList() {

    }

    @Override
    public void clearDisposeable() {
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.clear();
        }
    }
}
