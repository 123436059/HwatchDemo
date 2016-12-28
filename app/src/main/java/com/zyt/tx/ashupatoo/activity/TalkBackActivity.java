package com.zyt.tx.ashupatoo.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyt.tx.ashupatoo.R;
import com.zyt.tx.ashupatoo.inter.MediaPlayControl;
import com.zyt.tx.ashupatoo.util.FileUtils;
import com.zyt.tx.ashupatoo.util.MediaUitl;
import com.zyt.tx.ashupatoo.util.VoiceRecorder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TalkBackActivity extends BaseActivity implements MediaPlayControl {

    private ImageView ivMic;
    private List<Drawable> mIcBgs;
    private VoiceRecorder mVoiceRecorder;
    private long mCurrentTime;
    private boolean mFlag;
    private PowerManager.WakeLock mWakeLock;
    private MediaPlayer mMediaPlayer;
    private View viewMic;
    private TextView tvHint;
    private SimpleDateFormat mSdf;

    @Override
    public int getLayoutId() {
        return R.layout.activity_talk_back;
    }

    @Override
    public void initWork(Bundle savedInstanceState) {
        tvHint = (TextView) findViewById(R.id.tvHint);
        View talkButton = findViewById(R.id.ll_talk);
        ivMic = (ImageView) findViewById(R.id.ivMic);
        viewMic = findViewById(R.id.MicView);
        initDrawables();
        initWakeLock();
        initUtil();
        talkButton.setOnTouchListener(new TalkListener());
        mVoiceRecorder = new VoiceRecorder(mVoiceHandler);
    }

    private void initUtil() {
        mSdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    }

    private void initWakeLock() {
        mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "talkLock");
    }

    private void initDrawables() {
        mIcBgs = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            String fieldName = String.format("record_animate_0%s.png", (i + 1));
            Log.d("taxi", "fieldName =" + fieldName);
            int resId = getResourceIdByName(fieldName);
            if (resId != -1) {
                mIcBgs.add(getResources().getDrawable(resId));
            }
        }
    }

    private Integer getResourceIdByName(String fieldName) {
        try {
            Field field = R.drawable.class.getField(fieldName);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Handler mVoiceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VoiceRecorder.WHAT_UPDATE_MIC:
                    int index = msg.arg1;
                    if (index > -1 && index < mIcBgs.size()) {
                        ivMic.setImageDrawable(mIcBgs.get(index));
                    }
                    break;

                case VoiceRecorder.WHAT_STOP:
                    int length = msg.arg1;
                    String voicePath = (String) msg.obj;
                    //数据库操作，greenDao

                    break;
            }
        }
    };

    private class TalkListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!mVoiceRecorder.isRecording) {
                        if (System.currentTimeMillis() - mCurrentTime < 0.4 * 1000) {
                            return true;
                        }
                        mFlag = true;
                        if (!FileUtils.isExitsSdcard()) {
                            //need sd
                            return false;
                        }
                        view.setPressed(true);
                        mWakeLock.acquire();
                        stopPlay();
                        viewMic.setVisibility(View.VISIBLE);
                        tvHint.setText("上滑取消录音");
                        long curTime = System.currentTimeMillis();
                        String mediaPath = MediaUitl.getLocalVoicePath(mContext)
                                + mSdf.format(curTime) + ".amr";
                        File savePath = new File(mediaPath);
                        try {
                            savePath.createNewFile();
                            mVoiceRecorder.startRecording(savePath, mContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                            view.setPressed(false);
                            if (mWakeLock.isHeld()) {
                                mWakeLock.release();
                            }

                            if (mVoiceRecorder != null) {
                                mVoiceRecorder.discardRecord();
                            }
                            viewMic.setVisibility(View.INVISIBLE);
                        }
                        return true;
                    }


                case MotionEvent.ACTION_MOVE:
                    if (mVoiceRecorder.isRecording) {
                        if (mFlag) {
                            if (motionEvent.getY() < 0) {
                                tvHint.setText("松开取消录制");
                            } else {
                                tvHint.setText("上滑取消录音");
                            }
                        }
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                    if (mVoiceRecorder.isRecording) {
                        mCurrentTime = System.currentTimeMillis();
                        if (mFlag) {
                            return true;
                        }
                        view.setPressed(false);
                        viewMic.setVisibility(View.INVISIBLE);
                        if (mWakeLock.isHeld()) {
                            mWakeLock.release();
                        }

                        if (motionEvent.getY() < 0) {
                            mVoiceRecorder.discardRecord();
                        } else {
                            int length = mVoiceRecorder.stopRecord();
                            if (length > 0) {
                                //TODO get voice entity and commit

                            } else {
                                //TODO deal with fail

                            }
                        }
                    }
                    return true;
            }
            return false;
        }
    }


    @Override
    public boolean isPlaying() {
        boolean isPlay = false;
        if (mMediaPlayer != null) {
            try {
                isPlay = mMediaPlayer.isPlaying();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isPlay;
    }

    @Override
    public void stopPlay() {
        if (mMediaPlayer != null) {
            if (isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
    }

    @Override
    public void play(String playPath) {
        stopPlay();
        try {
            //TODO update voice info
            mMediaPlayer = new MediaPlayer();
            //TODO set play mode
            mMediaPlayer.setDataSource(playPath);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //TODO 停止動畫
                    resetAudioMode();
                    if (mMediaPlayer != null) {
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                }
            });
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO 停止動畫
            resetAudioMode();
        }
    }

    private void resetAudioMode() {

    }
}

