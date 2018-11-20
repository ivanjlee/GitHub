package com.ivan.github.web;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;

import com.github.utils.L;

/**
 * a class used to hold some constant values
 *
 * @author  Ivan at 2017-10-05 17:38
 * @version v0.1
 * @since   v0.1
 */

public class WebActivity extends BaseActivity {

    private static final String EXTRA_KEY_URL = TAG + ".extra.url";
    private static final String EXTRA_KEY_TITLE = TAG + ".extra.title";

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private WebView mWebView;

    private String mUrl;
    private String mTitle;

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_KEY_URL, url);
        intent.putExtra(EXTRA_KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initToolbar(mToolbar);
        initData();
    }

    protected void initView() {
        mToolbar = this.findViewById(R.id.toolbar);
        mProgressBar = this.findViewById(R.id.progress_bar);
        mWebView = this.findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new GHWebViewClient());
        mWebView.setWebChromeClient(new GHWebChromeClient());
    }

    protected void initData() {
        mUrl = getIntent().getStringExtra(EXTRA_KEY_URL);
        mTitle = getIntent().getStringExtra(EXTRA_KEY_TITLE);
        setTitle(mTitle);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.app_bar_refresh:
                mWebView.loadUrl(mWebView.getUrl());
                return true;
            case R.id.app_bar_share:
                Snackbar.make(mWebView, "Not implemented yet!", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.app_bar_open:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    L.e(TAG, e);
                    Snackbar.make(mWebView, R.string.not_app_found_to_open_the_link, Snackbar.LENGTH_SHORT).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class GHWebViewClient extends BaseWebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }

    private class GHWebChromeClient extends BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (TextUtils.isEmpty(title)) {
                setTitle(view.getUrl());
            } else {
                setTitle(title);
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    protected void onDestroy() {
        mWebView.loadUrl("");
        try {
            mWebView.removeAllViews();
            mWebView.destroy();
        } catch (Exception e) {
            L.printStackTrace(e);
        }
        super.onDestroy();
    }
}
