package com.ivan.github.app.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        getWindow().getDecorView().setFitsSystemWindows(true);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(SplashActivity.this, LandingActivity.class), R.anim.alpha_in, R.anim.alpha_out);
                SplashActivity.this.finish();
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
