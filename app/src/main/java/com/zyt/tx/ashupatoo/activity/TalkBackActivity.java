package com.zyt.tx.ashupatoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zyt.tx.ashupatoo.R;

public class TalkBackActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_talk_back;
    }

    @Override
    public void initWork(Bundle savedInstanceState) {
        TextView tvHint = (TextView) findViewById(R.id.tvHint);
        View talkButton = findViewById(R.id.ll_talk);
        talkButton.setOnTouchListener(new TalkListener());

    }


    private class TalkListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
            }
            return false;
        }
    }
}

