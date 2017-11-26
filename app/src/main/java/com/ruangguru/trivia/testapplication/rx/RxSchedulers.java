package com.ruangguru.trivia.testapplication.rx;

import io.reactivex.Scheduler;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public interface RxSchedulers {
    Scheduler runOnBackground();

    Scheduler io();

    Scheduler compute();

    Scheduler androidThread();

    Scheduler internet();

    Scheduler newThread();
}
