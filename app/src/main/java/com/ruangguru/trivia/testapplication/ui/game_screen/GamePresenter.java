package com.ruangguru.trivia.testapplication.ui.game_screen;

import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;
import com.ruangguru.trivia.testapplication.rx.RxRetryFunction;
import com.ruangguru.trivia.testapplication.rx.RxSchedulers;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceMaybeObserver;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class GamePresenter implements GameInterface.Presenter {
    private final RxSchedulers rxSchedulers;
    private final GameModel gameModel;
    private final CompositeDisposable compositeDisposable;

    public GamePresenter(RxSchedulers rxSchedulers, GameModel gameModel, CompositeDisposable compositeDisposable) {
        this.rxSchedulers = rxSchedulers;
        this.gameModel = gameModel;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadQuestionList(GameActivity gameActivity, String difficulty, int categoryCode) {
        compositeDisposable.add(gameModel.isNetworkAvailable(gameActivity)
                .observeOn(rxSchedulers.androidThread())
                .filter(aBoolean -> true)
                .flatMap(aBoolean -> gameModel.loadQuestionList(difficulty, categoryCode))
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
                .retryWhen(new RxRetryFunction(3, 2000))
                .subscribe(gameActivity::sendSuccessResponse, gameActivity::sendErrorResponse));
    }

    @Override
    public void clearAndDispose() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }

    @Override
    public void loadPreviousSession(GameActivity gameActivity) {
        List<QuestionAnswer> questionAnswerList = gameModel.loadPreviousQuestionList();
        compositeDisposable.add(gameModel.checkSession()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
                .subscribeWith(new ResourceMaybeObserver<SessionRecord>() {
                    @Override
                    public void onSuccess(SessionRecord sessionRecord) {
                        gameActivity.continuePreviousGame(sessionRecord, questionAnswerList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
