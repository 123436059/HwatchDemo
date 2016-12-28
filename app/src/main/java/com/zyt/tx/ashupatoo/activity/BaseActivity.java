package com.zyt.tx.ashupatoo.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initWork(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        initWork(savedInstanceState);
    }
}
