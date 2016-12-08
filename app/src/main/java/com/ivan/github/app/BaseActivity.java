package com.ivan.github.app;

import android.content.Intent;
import android.support.annotation.AnimRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void startActivity(Intent intent, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterAnim, exitAnim);
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
    }
}
