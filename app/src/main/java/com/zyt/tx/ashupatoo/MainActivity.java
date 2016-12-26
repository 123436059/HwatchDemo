package com.zyt.tx.ashupatoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zyt.tx.ashupatoo.activity.BaseActivity;
import com.zyt.tx.ashupatoo.fragment.HomeFragment;
import com.zyt.tx.ashupatoo.fragment.MsgFragment;
import com.zyt.tx.ashupatoo.fragment.SettingFragment;
import com.zyt.tx.ashupatoo.view.MainTabView;

public class MainActivity extends BaseActivity implements MainTabView.onTabItemSelectedListener {

    private FragmentManager mFragManager;
    private MsgFragment mMsgFrag;
    private HomeFragment mHomeFragment;
    private SettingFragment mSettingFragment;

    private Fragment mContentFrag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWork(Bundle savedInstanceState) {
        initView();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        mFragManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mMsgFrag = new MsgFragment();
            mHomeFragment = new HomeFragment();
            mSettingFragment = new SettingFragment();
        } else {
            mMsgFrag = (MsgFragment) mFragManager.findFragmentByTag(MsgFragment.class.getSimpleName());
            mHomeFragment = (HomeFragment) mFragManager.findFragmentByTag(HomeFragment.class.getSimpleName());
            mSettingFragment = (SettingFragment) mFragManager.findFragmentByTag(SettingFragment.class.getSimpleName());
        }
        switchContentFragment(mContentFrag, mHomeFragment);
    }

    private void initView() {
        MainTabView mMainTab = (MainTabView) findViewById(R.id.mainTabView);
        mMainTab.setOnTabItemSelectedListener(this);
        mMainTab.setCurrentItem(1);
    }

    @Override
    public void onTabItemSelected(int position) {
        switch (position) {
            case MainTabView.MSG_CLICK:
                switchContentFragment(mContentFrag, mMsgFrag);
                break;

            case MainTabView.HOME_CLICK:
                switchContentFragment(mContentFrag, mHomeFragment);
                break;

            case MainTabView.SET_CLICK:
                switchContentFragment(mContentFrag, mSettingFragment);
                break;
        }
    }

    private void switchContentFragment(Fragment from, Fragment to) {
        if (mContentFrag == to) {
            return;
        }
        FragmentTransaction transaction = mFragManager.beginTransaction();
        if (from == null) {
            if (!to.isAdded()) {
                transaction.add(R.id.frame_content, to, to.getClass().getSimpleName()).commit();
            }
        } else {
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.frame_content, to, to.getClass().getSimpleName()).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
        mContentFrag = to;
    }
}
