package com.ferjuarez.simpleredditclient.presenters;

import android.util.Log;
import android.view.View;

import com.ferjuarez.simpleredditclient.models.RedditElement;
import com.ferjuarez.simpleredditclient.networking.RedditService;
import com.ferjuarez.simpleredditclient.networking.RetrofitManager;
import com.ferjuarez.simpleredditclient.utils.ui.RedditPostAdapter;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ferjuarez on 3/17/17.
 */

public class RedditPresenter extends BasePresenter {
    private static RedditPresenter mInstance = null;

    private Subscription mRedditSubscription;
    private RedditService mRedditService;

    private static final int PAGE_SIZE = 10;

    public RedditPresenter(){
        mRedditService = RetrofitManager.getInstance().getRedditService();
    }

    public static RedditPresenter getInstance(){
        if(mInstance == null){
            return new RedditPresenter();
        } else return mInstance;
    }

    public void getTops(){
        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> {
                            getView().onSuccess(tops.getData());
                        },
                        error -> {
                            if(error != null && error.getMessage() != null)
                                getView().onConnectionError(new Throwable(error.getMessage()));
                        }
                );
    }

    public void getPaginatedTops(String nextPage){
        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE, nextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> {
                            getView().onSuccess(tops.getData());
                        },
                        error -> {
                            if(error != null && error.getMessage() != null)
                                getView().onConnectionError(new Throwable(error.getMessage()));
                        }
                );
    }

    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    protected void cancelAllTasks() {
        mRedditSubscription.unsubscribe();
    }
}
