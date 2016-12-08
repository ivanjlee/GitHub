package com.ivan.github.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;

public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(LandingActivity.this, SplashActivity.class));
                LandingActivity.this.finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void finish() {
//        ActivityCompat.finishAfterTransition(this);
        super.finish();
    }
}
