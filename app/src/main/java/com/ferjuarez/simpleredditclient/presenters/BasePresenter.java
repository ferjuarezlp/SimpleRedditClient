package com.ferjuarez.simpleredditclient.presenters;

import com.ferjuarez.simpleredditclient.views.BaseView;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public abstract class BasePresenter<V extends BaseView> {

    private V mView;

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        cancelAllTasks();
    }

    public V getView() {
        return mView;
    }

    protected abstract void cancelAllTasks();
}
