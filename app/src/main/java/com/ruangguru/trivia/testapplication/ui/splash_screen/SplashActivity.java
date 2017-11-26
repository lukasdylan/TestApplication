package com.ruangguru.trivia.testapplication.ui.splash_screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ruangguru.trivia.testapplication.CoreApplication;
import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.ui.category_screen.CategoryActivity;
import com.ruangguru.trivia.testapplication.ui.splash_screen.sub_module.DaggerSplashComponent;
import com.ruangguru.trivia.testapplication.ui.splash_screen.sub_module.SplashModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSplashComponent.builder()
                .appComponent(CoreApplication.getAppComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.letsPlayBtn)
    void letsPlay(){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        splashPresenter.clearDisposeable();
        super.onDestroy();
    }
}
