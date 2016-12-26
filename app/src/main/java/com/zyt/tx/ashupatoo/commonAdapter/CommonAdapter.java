package com.zyt.tx.ashupatoo.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by MJS on 2016/12/24.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private int mLayoutId;
    private List<T> mList;
    private LayoutInflater mInflater;

    public abstract void convert(ViewHolder holder, T t, int position);

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mList = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
