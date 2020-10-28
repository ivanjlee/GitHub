package com.ivan.github.app.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ivan.github.R;
import com.ivan.github.account.Account;
import com.ivan.github.app.AppSettings;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.main.MainActivity;

/**
 * @author Ivan J. Lee
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiVisibility);
        getWindow().getDecorView().setFitsSystemWindows(true);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                navigation();
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    private void navigation() {
        if (AppSettings.isFirstLogin()) {
            startActivity(new Intent(SplashActivity.this, LandingActivity.class), R.anim.alpha_in, R.anim.alpha_out);
        } else {
            String auth = Account.getInstance().getAuthorization();
            if (TextUtils.isEmpty(auth)) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class), R.anim.alpha_in, R.anim.alpha_out);
            } else {
                Account.getInstance().loadUser();
                startActivity(new Intent(SplashActivity.this, MainActivity.class), R.anim.alpha_in, R.anim.alpha_out);
            }
        }
        SplashActivity.this.finish();
    }

    @Override
    public void finish() {
// ActivityCompat.finishAfterTransition(this);
        super.finish();
    }
}
