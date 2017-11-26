package com.ruangguru.trivia.testapplication.ui.game_screen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ruangguru.trivia.testapplication.data.constant.AppConstant;
import com.ruangguru.trivia.testapplication.data.network.QuestionListResponse;
import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;
import com.ruangguru.trivia.testapplication.data.service.APIService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class GameModel {

    private final APIService apiService;

    public GameModel(APIService apiService) {
        this.apiService = apiService;
    }

    private static boolean checkInternetConnection(GameActivity gameActivity) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) gameActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (mConnectivityManager != null) {
            activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }

    Observable<Boolean> isNetworkAvailable(GameActivity gameActivity) {
        return Observable.defer(() -> Observable.just(checkInternetConnection(gameActivity)));
    }

    Observable<QuestionListResponse> loadQuestionList(String difficulty, int categoryCode){
        return apiService.getQuestionList(String.valueOf(AppConstant.QUESTION_SIZE), String.valueOf(categoryCode), difficulty, AppConstant.QUESTION_TYPE);
    }

    List<QuestionAnswer> loadPreviousQuestionList(){
        return QuestionAnswer.getQAList();
    }

    Maybe<SessionRecord> checkSession(){
        return Maybe.create(e -> {
            try {
                SessionRecord sessionRecord = SessionRecord.getSessionData();
                if(sessionRecord!=null){
                    e.onSuccess(sessionRecord);
                }
            } catch (Exception ex){
                e.onError(ex);
            }
        });
    }
}
