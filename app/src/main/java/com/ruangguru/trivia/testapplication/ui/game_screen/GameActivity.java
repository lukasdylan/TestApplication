package com.ruangguru.trivia.testapplication.ui.game_screen;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.CoreApplication;
import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.custom_view.DisabledSwipeViewPager;
import com.ruangguru.trivia.testapplication.data.constant.AppConstant;
import com.ruangguru.trivia.testapplication.data.network.QuestionListResponse;
import com.ruangguru.trivia.testapplication.data.network.ResultResponse;
import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;
import com.ruangguru.trivia.testapplication.ui.game_screen.sub_module.DaggerGameComponent;
import com.ruangguru.trivia.testapplication.ui.game_screen.sub_module.GameModule;
import com.ruangguru.trivia.testapplication.ui.result_screen.ResultActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameInterface.View, GameInterface.AnswerAdapter {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.questionViewPager)
    DisabledSwipeViewPager questionViewPager;
    @BindView(R.id.nextBtn)
    Button nextBtn;
    @BindView(R.id.loadingLayout)
    LinearLayout loadingLayout;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    @BindView(R.id.questionNoTV)
    TextSwitcher questionNoTV;
    @BindView(R.id.rightAnswerTV)
    TextSwitcher rightAnswerTV;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.timeLimitTV)
    TextView timeLimitTV;

    @BindDrawable(R.drawable.ic_close_black_24dp)
    Drawable closeBtn;

    @BindColor(android.R.color.white)
    int whiteColor;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @Inject
    GamePresenter gamePresenter;

    private int categoryCode = 0;
    private String categoryName = "";
    private String difficultLevel = "";
    private int rightAnswerCount = 0;
    private BottomSheetDialog bottomSheetDialog;
    private int categoryIcon;
    private int questionAnsweredCount = 0;
    private boolean isErrorRequest = false;
    private boolean isContinueGame = false;
    private CountDownTimer timer;
    private boolean finishedGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGameComponent.builder()
                .appComponent(CoreApplication.getAppComponent())
                .gameModule(new GameModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryName = bundle.getString("category_name");
            isContinueGame = bundle.getBoolean("continue_game");
            if(!isContinueGame){
                categoryCode = bundle.getInt("category_code");
                difficultLevel = bundle.getString("difficult_level");
                categoryIcon = bundle.getInt("category_icon");
            }
        } else {
            Log.e("Bundle is empty", String.valueOf(true));
        }

        initViewComponent();
        if(isContinueGame){
            gamePresenter.loadPreviousSession(this);
        } else {
            gamePresenter.loadQuestionList(this, difficultLevel, categoryCode);
        }

    }

    @Override
    public void onBackPressed() {
        if(!isErrorRequest){
            bottomSheetDialog.show();
        } else {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    protected void onDestroy() {
        gamePresenter.clearAndDispose();
        cancelGameTimer();
        super.onDestroy();
    }

    @Override
    public void initViewComponent() {
        toolbar.setTitle(categoryName);
        toolbar.setTitleTextColor(whiteColor);
        toolbar.setNavigationIcon(closeBtn);
        toolbar.setNavigationOnClickListener(view1 -> onBackPressed());

        questionNoTV.setFactory(() -> {
            TextView textView = new TextView(GameActivity.this);
            textView.setGravity(Gravity.START);
            textView.setTextColor(colorPrimary);
            textView.setTextSize(15);
            return textView;
        });
        questionNoTV.setInAnimation(this, R.anim.fade_in);
        questionNoTV.setOutAnimation(this, R.anim.fade_out);

        rightAnswerTV.setFactory(() -> {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.END);
            textView.setTextSize(15);
            return textView;
        });
        rightAnswerTV.setInAnimation(this, R.anim.fade_in);
        rightAnswerTV.setOutAnimation(this, R.anim.fade_out);

        questionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == (AppConstant.QUESTION_SIZE - 1)) {
                    nextBtn.setText("Finish");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.item_bottomsheet_game, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        TextView continueTV = bottomSheetView.findViewById(R.id.continueTV);
        continueTV.setOnClickListener(v -> {
            int questionLeftCount = AppConstant.QUESTION_SIZE - questionAnsweredCount;
            SessionRecord.updateQuestionLeft(questionLeftCount, rightAnswerCount);
            bottomSheetDialog.dismiss();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        TextView notContinueTV = bottomSheetView.findViewById(R.id.notContinueTV);
        notContinueTV.setOnClickListener(v -> {
            QuestionAnswer.deleteData();
            SessionRecord.deleteData();
            bottomSheetDialog.dismiss();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        TextView cancelTV = bottomSheetView.findViewById(R.id.cancelTV);
        cancelTV.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void sendSuccessResponse(QuestionListResponse questionListResponse) {
        int responseCode = questionListResponse.getResponseCode();
        if (responseCode == 0) {
            List<ResultResponse> resultResponses = questionListResponse.getResults();
            if (!resultResponses.isEmpty()) {
                Calendar timeLimit = Calendar.getInstance();
                timeLimit.add(Calendar.MINUTE, 20);
                SessionRecord sessionRecord = new SessionRecord(categoryName, difficultLevel, categoryIcon, resultResponses.size(), 0, timeLimit);
                sessionRecord.save();

                questionNoTV.setText("Question no. 1");
                rightAnswerTV.setText("No right answered");
                List<QuestionFragment> fragmentList = new ArrayList<>();
                List<QuestionAnswer> questionAnswerList = new ArrayList<>();
                for (int i = 0; i < resultResponses.size(); i++) {
                    ResultResponse resultResponse = resultResponses.get(i);

                    List<String> answerList = resultResponse.getIncorrectAnswers();
                    answerList.add(resultResponse.getCorrectAnswer());
                    Collections.shuffle(answerList);

                    QuestionAnswer questionAnswer = new QuestionAnswer(
                            i + 1,
                            resultResponse.getQuestion(),
                            answerList,
                            resultResponse.getCorrectAnswer());
                    questionAnswerList.add(questionAnswer);

                    fragmentList.add(new QuestionFragment(questionAnswer, this));
                }
                QuestionAnswer.saveData(questionAnswerList);
                QuestionPagerAdapter questionPagerAdapter =
                        new QuestionPagerAdapter(getSupportFragmentManager(), fragmentList);
                questionViewPager.setAdapter(questionPagerAdapter);
                startGameTimer();
                loadingLayout.setVisibility(View.GONE);
            }
        } else {
            loadingLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            isErrorRequest = true;
        }
    }

    @Override
    public void sendErrorResponse(Throwable throwable) {
        isErrorRequest = true;
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void toResultPage() {
        SessionRecord.deleteData();
        QuestionAnswer.deleteData();
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("category_name", categoryName);
        bundle.putString("difficult_level", difficultLevel);
        bundle.putInt("right_answer_count", rightAnswerCount);
        bundle.putBoolean("finish_game", finishedGame);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void continuePreviousGame(SessionRecord sessionRecord, List<QuestionAnswer> questionAnswerList) {
        rightAnswerCount = sessionRecord.getRightAnswered();
        questionAnsweredCount = (AppConstant.QUESTION_SIZE - sessionRecord.getQuestionLeft());
        questionNoTV.setText("Question no. "+(questionAnsweredCount + 1));
        rightAnswerTV.setText(String.format(Locale.getDefault(), "%d right answered", rightAnswerCount));
        List<QuestionFragment> fragmentList = new ArrayList<>();
        for(int i = 0; i < questionAnswerList.size(); i++){
            fragmentList.add(new QuestionFragment(questionAnswerList.get(i), this));
        }
        QuestionPagerAdapter questionPagerAdapter =
                new QuestionPagerAdapter(getSupportFragmentManager(), fragmentList);
        questionViewPager.setAdapter(questionPagerAdapter);

        questionViewPager.setCurrentItem(questionAnsweredCount, true);
        startGameTimer();
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void startGameTimer() {
        Calendar currentTime = Calendar.getInstance();
        SessionRecord sessionRecord = SessionRecord.getSessionData();
        Calendar gameTimeLimit = sessionRecord.getTimeLimit();
        long timeLeft = gameTimeLimit.getTimeInMillis() - currentTime.getTimeInMillis();
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                timeLimitTV.setText(String.format("%s : %s", minutes, seconds));
            }

            @Override
            public void onFinish() {
                cancelGameTimer();
                toResultPage();
            }
        };
        timer.start();
    }

    @Override
    public void cancelGameTimer() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @OnClick(R.id.nextBtn)
    void next() {
        nextBtn.setVisibility(View.GONE);
        if (questionViewPager.getCurrentItem() == AppConstant.QUESTION_SIZE - 1) {
            finishedGame = true;
            toResultPage();
        } else {
            questionViewPager.setCurrentItem(questionViewPager.getCurrentItem() + 1, true);
            questionNoTV.setText(String.format(Locale.getDefault(),
                    "Question no. %d",
                    questionViewPager.getCurrentItem() + 1));
        }
    }

    @Override
    public void onResult(boolean isRight) {
        questionAnsweredCount += 1;
        nextBtn.setVisibility(View.VISIBLE);
        if (isRight) {
            rightAnswerCount += 1;
        }
        rightAnswerTV.setText(String.format(Locale.getDefault(), "%d right answered", rightAnswerCount));
        SessionRecord.updateQuestionLeft(AppConstant.QUESTION_SIZE - questionAnsweredCount, rightAnswerCount);
    }
}
