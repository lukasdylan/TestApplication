package com.ruangguru.trivia.testapplication.ui.category_screen;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.CoreApplication;
import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.custom_view.GridRecyclerView;
import com.ruangguru.trivia.testapplication.custom_view.GridSpacingItemDecoration;
import com.ruangguru.trivia.testapplication.custom_view.SessionChangeAlertDialog;
import com.ruangguru.trivia.testapplication.data.repository.CategoryItem;
import com.ruangguru.trivia.testapplication.data.repository.QuestionAnswer;
import com.ruangguru.trivia.testapplication.data.repository.SessionRecord;
import com.ruangguru.trivia.testapplication.ui.category_screen.sub_module.CategoryModule;
import com.ruangguru.trivia.testapplication.ui.category_screen.sub_module.DaggerCategoryComponent;
import com.ruangguru.trivia.testapplication.ui.game_screen.GameActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity implements CategoryInterface.View
        , CategoryInterface.Adapter, SessionChangeAlertDialog.DialogCallback{

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.categoryRV)
    GridRecyclerView categoryRV;
    @BindView(R.id.previousGameLayout)
    LinearLayout previousGameLayout;
    @BindView(R.id.categoryNameTV)
    TextView categoryNameTV;
    @BindView(R.id.questionLeftTV)
    TextView questionLeftTV;
    @BindView(R.id.timeLimitTV)
    TextView timeLimitTV;

    @BindDrawable(R.drawable.ic_arrow_back)
    Drawable backBtn;

    @BindColor(android.R.color.white)
    int whiteColor;

    @Inject
    CategoryPresenter categoryPresenter;

    private List<CategoryItem> categoryItemList;
    private int categoryCode = 0;
    private String categoryName = "";
    private int categoryIcon;
    private BottomSheetDialog bottomSheetDialog;
    private CountDownTimer timer;
    private Calendar gameTimeLimit;
    private boolean hasPreviousGame = false;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCategoryComponent.builder()
                .appComponent(CoreApplication.getAppComponent())
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        categoryItemList = categoryPresenter.loadCategoryList();
        initViewComponent();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void initViewComponent() {
        toolbar.setTitle("Select Category");
        toolbar.setTitleTextColor(whiteColor);
        toolbar.setNavigationIcon(backBtn);
        toolbar.setNavigationOnClickListener(view1 -> onBackPressed());

        CategoryListAdapter categoryListAdapter =  new CategoryListAdapter(categoryItemList, this);
        categoryRV.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRV.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        categoryRV.setHasFixedSize(true);
        categoryRV.setAdapter(categoryListAdapter);

        bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.item_bottomsheet_difficult_level, null);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(bottomSheetView);
        TextView easyBtn = bottomSheetView.findViewById(R.id.easyBtn);
        TextView mediumBtn = bottomSheetView.findViewById(R.id.mediumBtn);
        TextView hardBtn = bottomSheetView.findViewById(R.id.hardBtn);

        easyBtn.setOnClickListener(v -> onStartGame("easy"));
        mediumBtn.setOnClickListener(v -> onStartGame("medium"));
        hardBtn.setOnClickListener(v -> onStartGame("hard"));
    }

    @Override
    public void onCategorySelect(int position) {
        selectedPosition = position;
        if(hasPreviousGame){
            SessionChangeAlertDialog sessionChangeAlertDialog = new SessionChangeAlertDialog(this, this);
            sessionChangeAlertDialog.show();
        } else {
            categoryCode = categoryItemList.get(position).getCategoryCode();
            categoryName = categoryItemList.get(position).getCategoryName();
            categoryIcon = categoryItemList.get(position).getCategoryIcon();
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onStartGame(String difficulty) {
        bottomSheetDialog.dismiss();
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("category_code", categoryCode);
        bundle.putString("category_name", categoryName);
        bundle.putInt("category_icon", categoryIcon);
        bundle.putString("difficult_level", difficulty);
        bundle.putBoolean("continue_game", false);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void showPreviousGameLayout(SessionRecord sessionRecord) {
        hasPreviousGame = true;
        categoryName = sessionRecord.getCategoryName();
        categoryNameTV.setText(categoryName);
        questionLeftTV.setText(String.format(Locale.getDefault()
                , "Question left: %d", sessionRecord.getQuestionLeft()));
        gameTimeLimit = sessionRecord.getTimeLimit();
        previousGameLayout.setVisibility(View.VISIBLE);
        startPreviousGameTimer();
    }

    @Override
    public void startPreviousGameTimer() {
        Calendar currentTime = Calendar.getInstance();
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
                hasPreviousGame = false;
                previousGameLayout.setVisibility(View.GONE);
                SessionRecord.deleteData();
                cancelPreviousGameTimer();
            }
        };
        timer.start();
    }

    @Override
    public void cancelPreviousGameTimer() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    public void hidePreviousGameLayout() {
        hasPreviousGame = false;
        previousGameLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        cancelPreviousGameTimer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryPresenter.checkPreviousGame(this);
    }

    @OnClick(R.id.continueBtn)
    void continueGame(){
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("continue_game", true);
        bundle.putString("category_name", categoryName);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onConfirm() {
        SessionRecord.deleteData();
        QuestionAnswer.deleteData();
        hidePreviousGameLayout();

        onCategorySelect(selectedPosition);
    }
}
