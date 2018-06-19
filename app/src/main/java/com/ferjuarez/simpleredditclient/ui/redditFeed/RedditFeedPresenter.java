package com.ferjuarez.simpleredditclient.ui.redditFeed;

import com.ferjuarez.simpleredditclient.networking.RedditService;
import com.ferjuarez.simpleredditclient.networking.RetrofitManager;
import com.ferjuarez.simpleredditclient.ui.base.BasePresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ferjuarez on 3/17/17.
 */

public class RedditFeedPresenter extends BasePresenter implements RedditFeedContract.Presenter  {
    private static RedditFeedPresenter mInstance = null;

    private Subscription mRedditSubscription;
    private RedditService mRedditService;

    private static final int PAGE_SIZE = 10;

    private RedditFeedPresenter(){
        mRedditService = RetrofitManager.getInstance().getRedditService();
    }

    public static RedditFeedPresenter getInstance(){
        if(mInstance == null){
            return new RedditFeedPresenter();
        } else return mInstance;
    }

    public void getTops(){
        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> getView().onSuccess(tops.getData()),
                        error -> {
                            if(error != null && error.getMessage() != null)
                                getView().onError(new Throwable(error.getMessage()));
                        }
                );
    }

    public void getPaginatedTops(String nextPage){
        mRedditSubscription = mRedditService.getPaginatedTop(PAGE_SIZE, nextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tops -> getView().onSuccess(tops.getData()),
                        error -> {
                            if(error != null && error.getMessage() != null)
                                getView().onError(new Throwable(error.getMessage()));
                        }
                );
    }

    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    public void doDispose() {
        mRedditSubscription.unsubscribe();
    }
}
