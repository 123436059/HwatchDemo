package com.zyt.tx.ashupatoo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zyt.tx.ashupatoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJS on 2016/12/23.
 */

public class MainTabView extends LinearLayout implements View.OnClickListener {


    public static final int MSG_CLICK = 0;
    public static final int HOME_CLICK = 1;
    public static final int SET_CLICK = 2;
    private int[] itemIds = {R.id.item_msg, R.id.item_home, R.id.item_set};
    private List<View> itemViews = new ArrayList<>();

    public interface onTabItemSelectedListener {
        void onTabItemSelected(int position);
    }

    private onTabItemSelectedListener mListener;

    public void setOnTabItemSelectedListener(onTabItemSelectedListener listener) {
        this.mListener = listener;
    }


    public MainTabView(Context context) {
        this(context, null);
    }

    public MainTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_main_tab, this, true);
        for (int itemId : itemIds) {
            View view = findViewById(itemId);
            view.setOnClickListener(this);
            itemViews.add(view);
        }
    }

    public void setCurrentItem(int position) {
        if (position < 0 || position >= itemIds.length) {
            return;
        }
        itemViews.get(position).performClick();
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        for (View view : itemViews) {
            view.setSelected(false);
            if (view.getId() == v.getId()) {
                view.setSelected(true);
            }
        }
        switch (v.getId()) {
            case R.id.item_msg:
                mListener.onTabItemSelected(MSG_CLICK);
                break;

            case R.id.item_home:
                mListener.onTabItemSelected(HOME_CLICK);
                break;

            case R.id.item_set:
                mListener.onTabItemSelected(SET_CLICK);
                break;
        }
    }
}
