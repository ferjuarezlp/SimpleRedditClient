package com.ferjuarez.simpleredditclient.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.ferjuarez.simpleredditclient.ui.DialogClickCallback;
import com.ferjuarez.simpleredditclient.ui.SimpleDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by ferjuarez on 3/15/17.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    private SimpleDialog simpleDialog;
    private KProgressHUD waitingHud;

    public void initToolBar(String title) {
        Toolbar toolbar = getActivityToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    public void showDialog(String message) {
        dismissDialog();
        simpleDialog = new SimpleDialog(
                this,
                null,
                () -> {},
                message
        );
        simpleDialog.show();
    }

    public void showInfoDialog(String message) {
        dismissDialog();
        simpleDialog = new SimpleDialog(
                this,
                () -> simpleDialog.dismiss(),
                message
        );
        simpleDialog.show();
    }


    @SuppressWarnings("unused")
    public void showErrorWithCallback(String message, DialogClickCallback callback) {
        dismissDialog();
        simpleDialog = new SimpleDialog(this, callback, () -> {}, message);
        simpleDialog.show();
    }


    public void dismissDialog() {
        if (simpleDialog != null) {
            if (simpleDialog.isShowing()) {
                simpleDialog.dismiss();
            }
        }
    }

    @SuppressWarnings("unused")
    public void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    @SuppressWarnings("unused")
    private KProgressHUD getDefaultWaitDialog(Context context, boolean cancelable) {
        return KProgressHUD.create(context)
                .setCancellable(cancelable)
                .setAnimationSpeed(2);
    }

    @SuppressWarnings("unused")
    public void showSimpleWaitDialog(Context context, String text) {
        showWaitDialog(context, text);
    }


    @SuppressWarnings("unused")
    public boolean isWaitDialogShowing() {
        return waitingHud != null && waitingHud.isShowing();
    }

    public void showWaitDialog(Context context, String title) {
        waitingHud = null;
        waitingHud = getDefaultWaitDialog(context, false)
                .setLabel(title)
                .show();
    }

    @SuppressWarnings("unused")
    public void showWaitDialog(Context context, String title, String subtitle) {
        waitingHud = getDefaultWaitDialog(context, false)
                .setLabel(title)
                .setDetailsLabel(subtitle)
                .show();
    }

    @SuppressWarnings("unused")
    public void dismissWaitDialog() {
        if (waitingHud != null) {
            if (waitingHud.isShowing()) {
                waitingHud.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleDialog = null;
        waitingHud = null;
    }

    protected abstract void bindViews();
    protected abstract Toolbar getActivityToolbar();
    protected abstract String getActivityTitle();
    protected abstract void initialize(Bundle savedInstanceState);
}