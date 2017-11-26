package com.ruangguru.trivia.testapplication.custom_view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;

import com.ruangguru.trivia.testapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 11/26/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class SessionChangeAlertDialog extends Dialog {

    private DialogCallback dialogCallback;

    public SessionChangeAlertDialog(@NonNull Context context, DialogCallback dialogCallback) {
        super(context);
        this.dialogCallback = dialogCallback;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.session_alert_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.noBtn)
    void cancelBtn(){
        dismiss();
    }

    @OnClick(R.id.yesBtn)
    void confirmBtn(){
        dismiss();
        dialogCallback.onConfirm();
    }

    public interface DialogCallback{
        void onConfirm();
    }
}
