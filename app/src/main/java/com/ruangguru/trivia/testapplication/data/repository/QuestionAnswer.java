package com.ruangguru.trivia.testapplication.data.repository;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.ruangguru.trivia.testapplication.CoreDatabase;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */
@Table(database = CoreDatabase.class)
public class QuestionAnswer extends BaseModel {
    @PrimaryKey(autoincrement = true, quickCheckAutoIncrement = true)
    private long id;

    @Column
    private int noQuestion;

    @Column
    private String question;

    @Column(typeConverter = StringListConverter.class)
    private List listAnswer;

    @Column
    private String rightAnswer;

    public QuestionAnswer(){

    }

    public QuestionAnswer(int noQuestion, String question, List<String> listAnswer, String rightAnswer) {
        this.noQuestion = noQuestion;
        this.question = question;
        this.listAnswer = listAnswer;
        this.rightAnswer = rightAnswer;
    }

    public long getId() {
        return id;
    }

    public int getNoQuestion() {
        return noQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getListAnswer() {
        return listAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNoQuestion(int noQuestion) {
        this.noQuestion = noQuestion;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setListAnswer(List<String> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public static void saveData(List<QuestionAnswer> questionAnswerList){
        FlowManager.getDatabase(CoreDatabase.class).executeTransaction(
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(QuestionAnswer.class))
                        .addAll(questionAnswerList)
                        .build()
        );
    }

    public static QuestionAnswer getQAbyNo(int noQuestion){
        return SQLite.select()
                .from(QuestionAnswer.class)
                .where(QuestionAnswer_Table.noQuestion.eq(noQuestion))
                .querySingle();
    }

    public static List<QuestionAnswer> getQAList(){
        return SQLite.select()
                .from(QuestionAnswer.class)
                .queryList();
    }

    public static void deleteData(){
        SQLite.delete(QuestionAnswer.class).execute();
        Log.d("Table deleted", String.valueOf(true));
    }
}
