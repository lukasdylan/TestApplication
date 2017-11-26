package com.ruangguru.trivia.testapplication.ui.game_screen;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<String> answerList = new ArrayList<>();
    private final String rightAnswer;
    private boolean alreadyAnswered = false;
    private boolean isRight = false;
    private int clickedPosition = 0;
    private final GameInterface.AnswerAdapter listener;

    AnswerAdapter(List<String> answerList, String rightAnswer, GameInterface.AnswerAdapter listener){
        this.answerList = answerList;
        this.rightAnswer = rightAnswer;
        this.listener = listener;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.bind(answerList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.answerCodeTV)
        TextView answerCodeTV;
        @BindView(R.id.answerTV)
        TextView answerTV;
        @BindView(R.id.resultTV)
        TextView resultTV;
        @BindView(R.id.mainCard)
        CardView mainCard;
        @BindView(R.id.mainLayout)
        LinearLayout mainLayout;

        @BindColor(R.color.correct_answer)
        int correctAnswer;
        @BindColor(R.color.wrong_answer)
        int wrongAnswer;
        @BindColor(android.R.color.white)
        int white;
        @BindColor(android.R.color.black)
        int black;

        @BindDrawable(R.drawable.correct_answer_background)
        Drawable correctBackground;
        @BindDrawable(R.drawable.wrong_answer_background)
        Drawable wrongBackground;
        @BindDrawable(R.drawable.black_outline)
        Drawable defaultBackground;

        AnswerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.mainCard)
        void resultCheck(){
            if(!alreadyAnswered){
                alreadyAnswered = true;
                clickedPosition = getAdapterPosition();
                if(answerList.get(clickedPosition).equalsIgnoreCase(rightAnswer)){
                    isRight = true;
                }
                notifyDataSetChanged();
                listener.onResult(isRight);
            }
        }

        private void bind(String answer, int position){
            answerTV.setText(answer.replace("&quot;", "\"")
                    .replace("&amp;", "&")
                    .replace("&#039;", "'"));
            switch (position){
                case 0:
                    answerCodeTV.setText("A");
                    break;
                case 1:
                    answerCodeTV.setText("B");
                    break;
                case 2:
                    answerCodeTV.setText("C");
                    break;
                case 3:
                    answerCodeTV.setText("D");
                    break;
            }

            if(alreadyAnswered){
                if(isRight && position==clickedPosition){
                    mainLayout.setBackground(correctBackground);
                    answerCodeTV.setTextColor(correctAnswer);
                    answerTV.setTextColor(white);
                    resultTV.setText("Correct Answer");
                    resultTV.setTextColor(white);
                    resultTV.setVisibility(View.VISIBLE);
                } else if(!isRight && position==clickedPosition){
                    mainLayout.setBackground(wrongBackground);
                    answerCodeTV.setTextColor(wrongAnswer);
                    answerTV.setTextColor(white);
                    resultTV.setText("Wrong Answer");
                    resultTV.setTextColor(white);
                    resultTV.setVisibility(View.VISIBLE);
                } else {
                    mainLayout.setBackground(defaultBackground);
                    answerTV.setTextColor(black);
                    resultTV.setTextColor(black);
                    resultTV.setVisibility(View.INVISIBLE);
                }
            } else {
                resultTV.setVisibility(View.INVISIBLE);
            }
        }
    }
}
