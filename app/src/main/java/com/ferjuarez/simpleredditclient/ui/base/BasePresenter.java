package com.ferjuarez.simpleredditclient.ui.base;

/**
 * Created by ferjuarez on 3/15/17.
 */
@SuppressWarnings("unused")
public abstract class BasePresenter<V extends ContractView> {

    private V mView;

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        doDispose();
    }

    public V getView() {
        return mView;
    }

    protected abstract void doDispose();
}
