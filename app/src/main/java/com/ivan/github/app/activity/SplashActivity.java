package com.ivan.github.app.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ivan.github.debug.DebugActivity;
import com.ivan.github.R;
import com.ivan.github.app.view.adapter.SplashPageAdapter;
import com.ivan.github.app.model.SplashData;

import java.util.ArrayList;
import java.util.List;

import github.design.widget.CirclePagerIndicator;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        SplashPageAdapter adapter = new SplashPageAdapter(this, getSplashData());
        mViewPager.setAdapter(adapter);
        CirclePagerIndicator indicator = (CirclePagerIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    private List<SplashData> getSplashData() {
        List<SplashData> result = new ArrayList<>();

        String uriPrefix = "android.resource://"+getPackageName()+"/mipmap/";

        SplashData d1 = new SplashData();
        d1.uri = uriPrefix + getString(R.string.splash_image_01);
        d1.title = getString(R.string.splash_title_01);
        d1.description = getString(R.string.splash_dec_01);
        result.add(d1);

        SplashData d2 = new SplashData();
        d2.uri = uriPrefix + getString(R.string.splash_image_02);
        d2.title = getString(R.string.splash_title_02);
        d2.description = getString(R.string.splash_dec_02);
        result.add(d2);

        SplashData d3 = new SplashData();
        d3.uri = uriPrefix + getString(R.string.splash_image_03);
        d3.title = getString(R.string.splash_title_03);
        d3.description = getString(R.string.splash_dec_03);
        result.add(d3);

        SplashData d4 = new SplashData();
        d4.uri = uriPrefix + getString(R.string.splash_image_04);
        d4.title = getString(R.string.splash_title_04);
        d4.description = getString(R.string.splash_dec_04);
        result.add(d4);

        return result;
    }

    public void signIn(View view) {
        Snackbar.make(mViewPager, "Sign in", Snackbar.LENGTH_LONG).show();
    }

    public void signUp(View view) {
//        Snackbar.make(mViewPager, "Sign up", Snackbar.LENGTH_LONG).show();
        startActivity(new Intent(SplashActivity.this, DebugActivity.class));
    }
}
