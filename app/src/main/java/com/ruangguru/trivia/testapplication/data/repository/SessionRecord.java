package com.ruangguru.trivia.testapplication.data.repository;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ruangguru.trivia.testapplication.CoreDatabase;

import java.util.Calendar;

/**
 * Created by Lukas Dylan Adisurya on 11/26/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

@Table(database = CoreDatabase.class)
public class SessionRecord extends BaseModel {
    @PrimaryKey(autoincrement = true, quickCheckAutoIncrement = true)
    private long id;

    @Column
    private String categoryName;

    @Column
    private int categoryIcon;

    @Column
    private String difficultLevel;

    @Column
    private int questionLeft;

    @Column
    private int rightAnswered;

    @Column
    private Calendar timeLimit;

    public SessionRecord(){

    }

    public SessionRecord(String categoryName, String difficultLevel, int categoryIcon, int questionLeft, int rightAnswered, Calendar timeLimit) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.questionLeft = questionLeft;
        this.timeLimit = timeLimit;
        this.difficultLevel = difficultLevel;
        this.rightAnswered = rightAnswered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public int getQuestionLeft() {
        return questionLeft;
    }

    public void setQuestionLeft(int questionLeft) {
        this.questionLeft = questionLeft;
    }

    public Calendar getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Calendar timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public int getRightAnswered() {
        return rightAnswered;
    }

    public void setRightAnswered(int rightAnswered) {
        this.rightAnswered = rightAnswered;
    }

    public static void deleteData(){
        SQLite.delete(SessionRecord.class).execute();
    }

    public static SessionRecord getSessionData(){
        return SQLite.select()
                .from(SessionRecord.class)
                .querySingle();
    }

    public static void updateQuestionLeft(int questionLeft, int rightAnswered){
        SessionRecord sessionRecord = getSessionData();
        if (sessionRecord != null) {
            sessionRecord.setQuestionLeft(questionLeft);
            sessionRecord.setRightAnswered(rightAnswered);
            sessionRecord.save();
        }
    }
}
