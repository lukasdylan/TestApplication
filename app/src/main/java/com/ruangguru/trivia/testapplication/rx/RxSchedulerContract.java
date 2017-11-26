package com.ruangguru.trivia.testapplication.rx;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class RxSchedulerContract implements RxSchedulers {

    private static final Executor backgroundExecutor = Executors.newCachedThreadPool();
    private static final Scheduler BACKGROUND_SCHEDULERS = Schedulers.from(backgroundExecutor);
    private static final Executor internetExecutor = Executors.newCachedThreadPool();
    public static final Scheduler INTERNET_SCHEDULERS = Schedulers.from(internetExecutor);

    @Override
    public Scheduler runOnBackground() {
        return BACKGROUND_SCHEDULERS;
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler androidThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler internet() {
        return INTERNET_SCHEDULERS;
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.newThread();
    }
}
