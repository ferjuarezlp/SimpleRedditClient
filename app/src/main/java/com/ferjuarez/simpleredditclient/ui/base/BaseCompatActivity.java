package com.ferjuarez.simpleredditclient.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.ferjuarez.simpleredditclient.ui.DialogClickCallback;
import com.ferjuarez.simpleredditclient.ui.SimpleDialog;

/**
 * Created by ferjuarez on 3/15/17.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    private SimpleDialog simpleDialog;

    public void initToolBar(String title) {
        Toolbar toolbar = getActivityToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(title);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void initToolBar() {
        Toolbar toolbar = getActivityToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void enableBackInToolbar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    public void setTitle(String title) {
        if(getSupportActionBar() != null)
        getSupportActionBar().setTitle(title);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleDialog = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected abstract void bindViews();
    protected abstract Toolbar getActivityToolbar();
    protected abstract String getActivityTitle();
    protected abstract void initialize(Bundle savedInstanceState);
}