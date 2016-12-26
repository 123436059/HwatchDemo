package com.zyt.tx.ashupatoo.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.adapter.HomeViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJS on 2016/12/23.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private List<View> mViews;
    private HomeViewPageAdapter mAdapter;
    private ViewPager mViewPager;
    private RadioGroup radioGroup;

    @Override
    public void initVariable() {
        mViews = new ArrayList<>();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        View page1 = getActivity().getLayoutInflater().inflate(R.layout.viewpager_layout_page1, null);
        View page2 = getActivity().getLayoutInflater().inflate(R.layout.viewpager_layout_page2, null);

        mViews.add(page1);
        mViews.add(page2);
        mViewPager.setAdapter(mAdapter = new HomeViewPageAdapter(mViews));

        mViewPager.setOnPageChangeListener(this);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        for (int i = 0; i < mViews.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setButtonDrawable(R.drawable.viewpager_indicator_bg);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mViewPager.setCurrentItem(i);
            }
        });
        radioGroup.check(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.check(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
