package com.ruangguru.trivia.testapplication.ui.game_screen.sub_module;

import com.ruangguru.trivia.testapplication.di.component.AppComponent;
import com.ruangguru.trivia.testapplication.ui.game_screen.GameActivity;

import dagger.Component;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@GameScope
@Component(modules = {GameModule.class} , dependencies = {AppComponent.class})
public interface GameComponent {
    void inject(GameActivity gameActivity);
}
