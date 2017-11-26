package com.ruangguru.trivia.testapplication.ui.game_screen;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.custom_view.GridRecyclerView;
import com.ruangguru.trivia.testapplication.custom_view.GridSpacingItemDecoration;
import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    @BindView(R.id.questionTV)
    TextView questionTV;
    @BindView(R.id.answerRV)
    GridRecyclerView answerRV;

    private Unbinder unbinder;
    private QuestionAnswer questionAnswer;
    private GameInterface.AnswerAdapter listener;

    public QuestionFragment(){

    }

    @SuppressLint("ValidFragment")
    public QuestionFragment(QuestionAnswer questionAnswer, GameInterface.AnswerAdapter listener) {
        // Required empty public constructor
        this.questionAnswer = questionAnswer;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionTV.setText(questionAnswer.getQuestion()
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&#039;", "'"));
        AnswerAdapter answerAdapter = new AnswerAdapter(questionAnswer.getListAnswer(), questionAnswer.getRightAnswer(), listener);
        answerRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        answerRV.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        answerRV.setHasFixedSize(true);
        answerRV.setAdapter(answerAdapter);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
