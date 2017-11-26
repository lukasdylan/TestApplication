package com.ruangguru.trivia.testapplication.ui.result_screen;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.R;

import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.resultIconIV)
    ImageView resultIconIV;
    @BindView(R.id.resultTV)
    TextView resultTV;
    @BindView(R.id.categoryNameTV)
    TextView categoryNameTV;
    @BindView(R.id.difficultLevelTV)
    TextView difficultLevelTV;
    @BindView(R.id.rightAnswerTV)
    TextView rightAnswerTV;

    @BindColor(android.R.color.white)
    int whiteColor;

    @BindDrawable(R.drawable.ic_close_red_24dp)
    Drawable failedIcon;
    @BindDrawable(R.drawable.ic_close_black_24dp)
    Drawable closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(whiteColor);
        toolbar.setNavigationIcon(closeBtn);
        toolbar.setNavigationOnClickListener(view1 -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String categoryName = bundle.getString("category_name");
            categoryNameTV.setText(categoryName);
            toolbar.setTitle(categoryName);
            String difficultLevel = bundle.getString("difficult_level");
            difficultLevelTV.setText(difficultLevel);
            int rightAnswer = bundle.getInt("right_answer_count");
            rightAnswerTV.setText(String.format(Locale.getDefault(), "%d", rightAnswer));
            boolean isFinishedGame = bundle.getBoolean("finish_game");
            if(!isFinishedGame){
                resultIconIV.setImageDrawable(failedIcon);
                resultTV.setText("Sorry, you failed to finish the game...");
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,0);
    }
}
