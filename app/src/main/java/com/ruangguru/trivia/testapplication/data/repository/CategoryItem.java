package com.ruangguru.trivia.testapplication.data.repository;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class CategoryItem {
    private String categoryName;
    private int backgroundImage;
    private int categoryIcon;
    private int categoryCode;

    public CategoryItem(String categoryName, int backgroundImage, int categoryIcon, int categoryCode) {
        this.categoryName = categoryName;
        this.backgroundImage = backgroundImage;
        this.categoryIcon = categoryIcon;
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public int getCategoryCode() {
        return categoryCode;
    }
}
