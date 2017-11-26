package com.ruangguru.trivia.testapplication.ui.category_screen.sub_module;

import com.ruangguru.trivia.testapplication.rx.RxSchedulers;
import com.ruangguru.trivia.testapplication.ui.category_screen.CategoryActivity;
import com.ruangguru.trivia.testapplication.ui.category_screen.CategoryModel;
import com.ruangguru.trivia.testapplication.ui.category_screen.CategoryPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@Module
public class CategoryModule {
    private final CategoryActivity categoryActivity;

    public CategoryModule(CategoryActivity categoryActivity){
        this.categoryActivity = categoryActivity;
    }

    @CategoryScope
    @Provides
    CategoryActivity provideActivity(){
        return categoryActivity;
    }

    @CategoryScope
    @Provides
    CategoryModel provideModel(){
        return new CategoryModel();
    }

    @CategoryScope
    @Provides
    CategoryPresenter providePresenter(CategoryModel model, RxSchedulers rxSchedulers){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return new CategoryPresenter(model, rxSchedulers, compositeDisposable);
    }
}
