package com.zyt.tx.ashupatoo.entity;

/**
 * Created by MJS on 2016/12/23.
 */

public class MsgInfoBean {
    private String time;
    private String content;
    private String address;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MsgInfoBean{" +
                "time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
