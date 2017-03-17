package com.ferjuarez.simpleredditclient.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.ferjuarez.simpleredditclient.R;

/**
 * Created by ferjuarez on 3/15/17.
 */
public class SimpleDialog extends Dialog {

    private Button mBtnOk;
    private Button mBtnCancel;
    private TextView mMessage;
    private TextView mExtraMsg;

    private DialogClickCallback mOkCallback;
    private DialogClickCallback mCancelCallback;
    private DialogClickCallback mTextCallback;


    public SimpleDialog(Context context, DialogClickCallback okFunction,
                        DialogClickCallback cancelFunction, String message) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mOkCallback = okFunction;
        mCancelCallback = cancelFunction;
        setContentView(R.layout.simple_dialog);
        initView();
        mBtnOk.setTransformationMethod(null);
        mBtnCancel.setTransformationMethod(null);
        setMessage(message);
        setCancelable(false);
    }

    public SimpleDialog(Context context, DialogClickCallback okFunction,
                        String message) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mOkCallback = okFunction;
        setContentView(R.layout.simple_dialog);
        initView();
        mBtnOk.setTransformationMethod(null);
        mBtnCancel.setTransformationMethod(null);
        setMessage(message);
        setCancelable(false);
        if(mCancelCallback == null){
            mBtnCancel.setVisibility(View.GONE);
        }
    }

    protected void initView() {
        mBtnOk = (Button) findViewById(R.id.btnDialogOk);
        mBtnCancel = (Button) findViewById(R.id.btnDialogCancel);
        mMessage = (TextView) findViewById(R.id.txtMessage);
        mExtraMsg = (TextView) findViewById(R.id.txtExtraMsg);
        mBtnOk.setOnClickListener(
                view -> {
                    if (mOkCallback != null) {
                        mOkCallback.onClick();
                    } else {
                        dismiss();
                    }
                }
        );
        mBtnCancel.setOnClickListener(view -> mCancelCallback.onClick());
        mExtraMsg.setOnClickListener(view -> mTextCallback.onClick());
    }

    private void setMessage(String msg) {
        mMessage.setText(msg);
    }

    public void setExtraMsg(String text, DialogClickCallback textFunction) {
        mExtraMsg.setVisibility(View.VISIBLE);
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        mExtraMsg.setText(content);
        mTextCallback = textFunction;
    }


}