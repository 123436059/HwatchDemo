package com.zyt.tx.ashupatoo.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    protected View mContentView;


    public abstract void initVariable();

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void init(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MLog("onCreate");
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MLog("onCreateView");
        mContentView = inflater.inflate(getLayoutId(), container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MLog("onActivityCreated");
        init(mContentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MLog("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLog("onDestroy");
    }

    protected void MLog(String content) {
        Log.d("taxi", TAG + "-->" + content);
    }
}
