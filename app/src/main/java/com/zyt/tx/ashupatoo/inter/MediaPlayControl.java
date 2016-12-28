package com.zyt.tx.ashupatoo.inter;

/**
 * Created by MJS on 2016/12/27.
 */

public interface MediaPlayControl {
    void play(String path);

    void stopPlay();

    boolean isPlaying();
}
