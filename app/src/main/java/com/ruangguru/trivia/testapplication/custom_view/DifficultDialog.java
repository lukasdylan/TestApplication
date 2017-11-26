package com.ruangguru.trivia.testapplication.custom_view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ruangguru.trivia.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 11/25/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class DifficultDialog extends Dialog {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private DialogCallback dialogCallback;
    private String difficultLevel = "easy";

    public DifficultDialog(@NonNull Context context, DialogCallback dialogCallback) {
        super(context);
        this.dialogCallback = dialogCallback;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_difficulty);
        ButterKnife.bind(this);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            difficultLevel = rb.getText().toString().toLowerCase();
        });
    }

    @OnClick(R.id.cancelBtn)
    void cancelDialog(){
        dismiss();
    }

    @OnClick(R.id.startBtn)
    void startGame(){
        dismiss();
        dialogCallback.onStartGame(difficultLevel);
    }

    public interface DialogCallback{
        void onStartGame(String difficulty);
    }
}
