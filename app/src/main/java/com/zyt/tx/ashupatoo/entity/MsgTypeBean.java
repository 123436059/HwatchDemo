package com.zyt.tx.ashupatoo.entity;

/**
 * Created by MJS on 2016/12/24.
 */

public class MsgTypeBean {

    private String text;
    private int sourceId;

    public MsgTypeBean(String text, int sourceId) {
        this.text = text;
        this.sourceId = sourceId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
