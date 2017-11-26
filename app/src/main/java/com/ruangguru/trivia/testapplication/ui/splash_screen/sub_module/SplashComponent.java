package com.ruangguru.trivia.testapplication.ui.splash_screen.sub_module;

import com.ruangguru.trivia.testapplication.di.component.AppComponent;
import com.ruangguru.trivia.testapplication.ui.splash_screen.SplashActivity;

import dagger.Component;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@SplashScope
@Component(modules = {SplashModule.class} , dependencies = {AppComponent.class})
public interface SplashComponent {
    void inject(SplashActivity activity);
}
