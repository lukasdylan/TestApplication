package com.ruangguru.trivia.testapplication.ui.game_screen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.ruangguru.trivia.testapplication.data.constant.AppConstant;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class QuestionPagerAdapter extends FragmentPagerAdapter {

    private List<QuestionFragment> questionFragmentList;

    QuestionPagerAdapter(FragmentManager fm, List<QuestionFragment> questionFragmentList) {
        super(fm);
        this.questionFragmentList = questionFragmentList;
    }

    @Override
    public int getCount() {
        return questionFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return questionFragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
