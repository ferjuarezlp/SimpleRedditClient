package com.ferjuarez.simpleredditclient.views;

/**
 * Created by ferjuarez on 3/15/17.
 */
public interface BaseView {
    void onSuccess(Object result);
    void onConnectionError(Throwable error);
}