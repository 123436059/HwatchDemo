package com.zyt.tx.ashupatoo.util;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.RunnableFuture;

/**
 * Created by MJS on 2016/12/27.
 */

public class VoiceRecorder {

    public static final int WHAT_UPDATE_MIC = 0x01;
    public static final int WHAT_STOP = 0x02;

    private final Handler mHandler;
    private File mFile;
    private MediaRecorder mRecorder;

    private static final int DEFAULT_MAX_DURATION = 10;
    private int maxRecordDuration = 0;
    private boolean isRecording = false;
    private long startTime;

    public VoiceRecorder(Handler handler) {
        mHandler = handler;
    }

    public String startRecording(File file, Context context) {
        mFile = null;

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        maxRecordDuration = DEFAULT_MAX_DURATION;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        mRecorder.setAudioChannels(1);
        mRecorder.setAudioSamplingRate(800);
        mRecorder.setAudioEncodingBitRate(1);
        mFile = file;
        mRecorder.setOutputFile(mFile.getAbsolutePath());
        try {
            mRecorder.prepare();
            isRecording = true;
            mRecorder.start();
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, maxRecordDuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRecording) {
                    //获取音量大小的方法，需要隔一段时间获取一次，所以要放在一个新的线程里来执行。
                    mHandler.obtainMessage(WHAT_UPDATE_MIC
                            , mRecorder.getMaxAmplitude() * 13 / 32767).sendToTarget();
                    SystemClock.sleep(100);  //没隔100毫秒推送一次音量值
                }
            }
        }).start();
        startTime = new Date().getTime();
        return mFile == null ? null : mFile.getAbsolutePath();
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            isRecording = true;
            Message msg = mHandler.obtainMessage();
            msg.what = WHAT_STOP;
            msg.arg1 = stopRecord();
            msg.obj = getVoiceFilePath();
        }
    };


    public String getVoiceFilePath() {
        return mFile.getAbsolutePath();
    }


    public int stopRecord() {
        if (mRecorder != null) {
            mHandler.removeCallbacks(mRunnable);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;

            if (mFile != null && mFile.exists() && mFile.isFile()
                    && mFile.length() == 0) {//录音失败的情况
                mFile.delete();
                return -1011;
            }

            int duration = (int) ((new Date().getTime() - startTime) / 1000);
            isRecording = false;
            return duration;
        }
        return 0;
    }
}
