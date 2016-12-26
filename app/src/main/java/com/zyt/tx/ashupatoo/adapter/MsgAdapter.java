package com.zyt.tx.ashupatoo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.entity.MsgInfoBean;

import java.util.List;

/**
 * Created by MJS on 2016/12/23.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgInfoBean> mList;

    public interface onItemClickListener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }

    onItemClickListener mListener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    public MsgAdapter(List<MsgInfoBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_msg, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MsgInfoBean bean = mList.get(position);
        holder.tvAddress.setText(bean.getAddress());
        holder.tvContent.setText(bean.getContent());
        holder.tvTime.setText(bean.getTime());

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mListener.onItemLongClick(view, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        View vTopLine, vBottomLine;
        ImageView ivType;
        TextView tvType;
        TextView tvContent;
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.msg_time_tv);
            vTopLine = itemView.findViewById(R.id.msg_top_line);
            vBottomLine = itemView.findViewById(R.id.msg_bottom_line);
            ivType = (ImageView) itemView.findViewById(R.id.iv_msg_type);
            tvType = (TextView) itemView.findViewById(R.id.msg_type_tv);
            tvContent = (TextView) itemView.findViewById(R.id.msg_content_tv);
            tvAddress = (TextView) itemView.findViewById(R.id.msg_address_tv);
        }
    }
}

