package com.ruangguru.trivia.testapplication.ui.game_screen.sub_module;

import com.ruangguru.trivia.testapplication.data.service.APIService;
import com.ruangguru.trivia.testapplication.rx.RxSchedulers;
import com.ruangguru.trivia.testapplication.ui.game_screen.GameActivity;
import com.ruangguru.trivia.testapplication.ui.game_screen.GameModel;
import com.ruangguru.trivia.testapplication.ui.game_screen.GamePresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */
@Module
public class GameModule {
    private final GameActivity gameActivity;

    public GameModule(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }

    @GameScope
    @Provides
    GameActivity provideActivity(){
        return gameActivity;
    }

    @GameScope
    @Provides
    GameModel provideModel(APIService apiService){
        return new GameModel(apiService);
    }

    @GameScope
    @Provides
    GamePresenter providePresenter(RxSchedulers rxSchedulers, GameModel gameModel){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return new GamePresenter(rxSchedulers, gameModel, compositeDisposable);
    }
}
