package com.ruangguru.trivia.testapplication.ui.game_screen;

import com.ruangguru.trivia.testapplication.data.network.QuestionListResponse;
import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public interface GameInterface {
    interface View{
        void initViewComponent();
        void sendSuccessResponse(QuestionListResponse questionListResponse);
        void sendErrorResponse(Throwable throwable);
        void toResultPage();
        void continuePreviousGame(SessionRecord sessionRecord, List<QuestionAnswer> questionAnswerList);
        void startGameTimer();
        void cancelGameTimer();
    }

    interface Presenter{
        void loadQuestionList(GameActivity gameActivity, String difficulty, int categoryCode);
        void clearAndDispose();
        void loadPreviousSession(GameActivity gameActivity);
    }

    interface AnswerAdapter{
        void onResult(boolean isRight);
    }
}
