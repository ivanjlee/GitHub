package com.ivan.github.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.design.widget.CirclePagerIndicator;
import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.login.model.SplashData;
import com.ivan.github.web.UrlConst;
import com.ivan.github.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan
 */
public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ViewGroup contentView = findViewById(android.R.id.content);
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        contentView.setPadding(0, statusBarHeight, 0, 0);
        contentView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        initView();
    }

    private void initView() {
        ViewPager mViewPager = findViewById(R.id.view_pager);
        SplashPageAdapter adapter = new SplashPageAdapter(this, getSplashData());
        mViewPager.setAdapter(adapter);
        CirclePagerIndicator indicator = findViewById(R.id.indicator);
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
        startActivityForResult(new Intent(LandingActivity.this, LoginActivity.class), LoginConsts.REQUEST_CODE_LOGIN);
    }

    public void signUp(View view) {
        WebActivity.start(this, UrlConst.GITHUB_REGISTER, getString(R.string.join_github));
    }

    public void explore(View view) {
        WebActivity.start(this, UrlConst.GITHUB_BASE_URL, "GitHub");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginConsts.REQUEST_CODE_LOGIN &&  resultCode == LoginConsts.RESULT_CODE_FINISH) {
            this.finish();
        }
    }
}
