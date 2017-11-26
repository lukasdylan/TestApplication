package com.ruangguru.trivia.testapplication.ui.category_screen;

import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.data.constant.AppConstant;
import com.ruangguru.trivia.testapplication.data.repository.CategoryItem;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class CategoryModel {
    List<CategoryItem> getCategoryList() {
        List<CategoryItem> categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem(AppConstant.CATEGORY_GENERAL_KNOWLEDGE_NAME, R.drawable.mask, R.drawable.ic_shape, AppConstant.CATEGORY_GENERAL_KNOWLEDGE_CODE));
        categoryItemList.add(new CategoryItem("Entertainment: Books", R.drawable.mask_2, R.drawable.ic_combined_shape, 10));
        categoryItemList.add(new CategoryItem("Entertainment: Film", R.drawable.mask_3, R.drawable.ic_shape_2, 11));
        categoryItemList.add(new CategoryItem("Entertainment: Music", R.drawable.mask_4, R.drawable.ic_combined_shape_3, 12));
        categoryItemList.add(new CategoryItem("Entertainment: Games", R.drawable.mask_5, R.drawable.ic_shape_3, 15));
        categoryItemList.add(new CategoryItem("Entertainment: TV", R.drawable.mask_6, R.drawable.ic_group, 14));
        categoryItemList.add(new CategoryItem("Science: Computers", R.drawable.mask_7, R.drawable.ic_combined_shape_2, 18));
        categoryItemList.add(new CategoryItem("Celebrities", R.drawable.mask_8, R.drawable.ic_combined_shape_6, 26));
        categoryItemList.add(new CategoryItem("History", R.drawable.mask_9, R.drawable.ic_combined_shape_4, 23));
        categoryItemList.add(new CategoryItem("Animals", R.drawable.mask_10, R.drawable.ic_combined_shape_5, 27));
        return categoryItemList;
    }

    Maybe<SessionRecord> checkSession() {
        return Maybe.create(e -> {
            try {
                SessionRecord sessionRecord = SessionRecord.getSessionData();
                if (sessionRecord != null) {
                    e.onSuccess(sessionRecord);
                } else {
                    e.onComplete();
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }
}
