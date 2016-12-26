package com.zyt.tx.ashupatoo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.entity.MsgTypeBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MJS on 2016/12/24.
 */

public class MsgTypeAdapter extends RecyclerView.Adapter<MsgTypeAdapter.TypeViewHolder> {
    private List<MsgTypeBean> mData;

    private OnTypeItemCilckListener mListener;
    public interface OnTypeItemCilckListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnTypeItemCilckListener listener) {
        this.mListener = listener;
    }

    public MsgTypeAdapter(List<MsgTypeBean> list) {
        mData = list;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_msg_type, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TypeViewHolder holder, int position) {
        holder.tvType.setText(mData.get(position).getText());
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvType;

        public TypeViewHolder(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
        }
    }
}
