package com.zyt.tx.ashupatoo.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.adapter.MsgAdapter;
import com.zyt.tx.ashupatoo.commonAdapter.CommonAdapter;
import com.zyt.tx.ashupatoo.commonAdapter.ViewHolder;
import com.zyt.tx.ashupatoo.entity.MsgInfoBean;
import com.zyt.tx.ashupatoo.entity.MsgTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJS on 2016/12/23.
 */

public class MsgFragment extends BaseFragment implements MsgAdapter.onItemClickListener, View.OnClickListener {
    private ImageView ivSelect;
    private RecyclerView mRecyclerView;
    private List<MsgInfoBean> mList;
    private MsgAdapter mAdapter;
    private List<MsgTypeBean> mTypeList;
    private TextView tvTitle;
    private PopupWindow mPopWindow;
    private CommonAdapter<MsgTypeBean> mCommonAdapter;

    @Override
    public void initVariable() {
        mList = new ArrayList<>();
        mTypeList = new ArrayList<>();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    public void init(View view) {
        ivSelect = (ImageView) view.findViewById(R.id.ivSelected);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ivSelect.setOnClickListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MsgAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        initData();
        initPop();
        mAdapter.setOnItemClickListener(this);
    }

    private void initPop() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null);
        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        View vExite = contentView.findViewById(R.id.ivExit);
        vExite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });
        RecyclerView typeRecyclerView = (RecyclerView) contentView.findViewById(R.id.typeRecyclerView);
        typeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        typeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommonAdapter = new CommonAdapter<MsgTypeBean>(getContext(), R.layout.item_msg_type, mTypeList) {
            @Override
            public void convert(ViewHolder holder, MsgTypeBean msgTypeBean, int position) {
                TextView textView = holder.getView(R.id.tvType);
                textView.setText(msgTypeBean.getText());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopWindow.dismiss();
                    }
                });
            }
        };
        typeRecyclerView.setAdapter(mCommonAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            MsgInfoBean bean = new MsgInfoBean();
            bean.setAddress("大学城");
            bean.setTime("2016年12月23号");
            bean.setContent("车辆发生碰撞");
            mList.add(bean);

            MsgTypeBean typeBean = new MsgTypeBean("低电量", i);
            mTypeList.add(typeBean);
        }
        mAdapter.notifyDataSetChanged();
    }

    /*
    适配器点击事件
     */
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "单击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(), "长按", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*
    view点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSelected:
                showPopUp();
                break;
        }
    }

    private void showPopUp() {
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.showAtLocation(tvTitle, Gravity.CENTER | Gravity.TOP, 0, 0);
    }
}
