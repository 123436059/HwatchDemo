package com.zyt.tx.ashupatoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.activity.TalkBackActivity;

import java.util.List;

/**
 * Created by MJS on 2016/12/24.
 */

public class HomeViewPageAdapter extends PagerAdapter implements View.OnClickListener {

    private final Context mContext;
    private List<View> mViews;

    public HomeViewPageAdapter(@NonNull List<View> views, Context context) {
        mContext = context;
        mViews = views;
        for (View view : views) {
            initViewListener(view);
        }
    }

    private void initViewListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                initViewListener(viewGroup.getChildAt(i));
            }
        } else {
            if (view instanceof TextView) {
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("taxi", "instantiateItem container=" + container.getClass().getSimpleName());
        View view = mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Log.d("taxi", "destroyItem container=" + container.getClass().getSimpleName());
        container.removeView(mViews.get(position));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home1:
                mContext.startActivity(new Intent(mContext, TalkBackActivity.class));
                break;

            case R.id.home2:
                Log.d("taxi", "2");
                break;

            case R.id.home3:
                Log.d("taxi", "3");
                break;
            case R.id.home4:
                Log.d("taxi", "4");
                break;

            case R.id.home5:
                Log.d("taxi", "5");
                break;

            case R.id.home6:
                Log.d("taxi", "6");
                break;

            case R.id.home7:
                Log.d("taxi", "7");
                break;

            case R.id.home8:
                Log.d("taxi", "8");
                break;

            case R.id.home9:
                Log.d("taxi", "9");
                break;
        }
    }
}
