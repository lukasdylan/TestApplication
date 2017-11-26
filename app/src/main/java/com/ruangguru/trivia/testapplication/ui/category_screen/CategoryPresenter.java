package com.ruangguru.trivia.testapplication.ui.category_screen;

import android.util.Log;

import com.ruangguru.trivia.testapplication.data.repository.CategoryItem;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;
import com.ruangguru.trivia.testapplication.rx.RxSchedulers;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceMaybeObserver;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class CategoryPresenter implements CategoryInterface.Presenter {
    private final CategoryModel model;
    private final RxSchedulers rxSchedulers;
    private final CompositeDisposable compositeDisposable;

    public CategoryPresenter(CategoryModel model, RxSchedulers rxSchedulers, CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        this.model = model;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public List<CategoryItem> loadCategoryList() {
        return model.getCategoryList();
    }

    @Override
    public void checkPreviousGame(CategoryActivity categoryActivity) {
        compositeDisposable.add(model.checkSession()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
                .subscribeWith(new ResourceMaybeObserver<SessionRecord>() {
                    @Override
                    public void onSuccess(SessionRecord sessionRecord) {
                        categoryActivity.showPreviousGameLayout(sessionRecord);
                        Log.d("Success", String.valueOf(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryActivity.hidePreviousGameLayout();
                        Log.d("Error", String.valueOf(true));
                    }

                    @Override
                    public void onComplete() {
                        categoryActivity.hidePreviousGameLayout();
                        Log.d("Complete", String.valueOf(true));
                    }
                }));
    }
}
