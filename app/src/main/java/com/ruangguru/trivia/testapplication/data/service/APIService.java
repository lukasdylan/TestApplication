package com.ruangguru.trivia.testapplication.data.service;

import com.ruangguru.trivia.testapplication.data.constant.AppConstant;
import com.ruangguru.trivia.testapplication.data.network.QuestionListResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public interface APIService {
    @GET(AppConstant.QUESTION_URL)
    Observable<QuestionListResponse> getQuestionList(@Query("amount") String amount,
                                                     @Query("category") String category,
                                                     @Query("difficulty") String difficulty,
                                                     @Query("type") String type);
}
