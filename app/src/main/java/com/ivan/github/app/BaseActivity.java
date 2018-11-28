package com.ivan.github.app;

import android.content.Intent;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getName();

    protected void startActivity(Intent intent, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterAnim, exitAnim);
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
    }

    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void initToolbar(@IdRes int id) {
        Toolbar toolbar = findViewById(id);
        initToolbar(toolbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                break;
            case KeyEvent.KEYCODE_MENU:
                onMenuPressed();
                break;
            case KeyEvent.KEYCODE_HOME:
                onHomePressed();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onMenuPressed() {}

    public void onHomePressed() {}
}
