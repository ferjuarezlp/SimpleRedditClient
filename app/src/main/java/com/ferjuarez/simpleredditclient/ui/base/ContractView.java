package com.ferjuarez.simpleredditclient.ui.base;

public interface ContractView {

    void onSuccess(Object result);

    void onError(Throwable error);

    void showLoading(boolean visibility);

    void showError(String error);

}