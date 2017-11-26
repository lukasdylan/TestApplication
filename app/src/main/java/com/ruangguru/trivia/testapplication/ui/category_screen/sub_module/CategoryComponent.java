package com.ruangguru.trivia.testapplication.ui.category_screen.sub_module;

import com.ruangguru.trivia.testapplication.di.component.AppComponent;
import com.ruangguru.trivia.testapplication.ui.category_screen.CategoryActivity;

import dagger.Component;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@CategoryScope
@Component(modules = {CategoryModule.class} , dependencies = {AppComponent.class})
public interface CategoryComponent {
    void inject(CategoryActivity activity);
}