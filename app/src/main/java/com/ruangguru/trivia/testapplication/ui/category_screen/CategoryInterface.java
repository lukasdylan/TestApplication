package com.ruangguru.trivia.testapplication.ui.category_screen;

import com.ruangguru.trivia.testapplication.data.repository.CategoryItem;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public interface CategoryInterface {
    interface View{
        void initViewComponent();
        void onStartGame(String difficult);
        void showPreviousGameLayout(SessionRecord sessionRecord);
        void startPreviousGameTimer();
        void cancelPreviousGameTimer();
        void hidePreviousGameLayout();
    }

    interface Presenter{
        List<CategoryItem> loadCategoryList();
        void checkPreviousGame(CategoryActivity categoryActivity);
    }

    interface Adapter{
        void onCategorySelect(int position);
    }
}
