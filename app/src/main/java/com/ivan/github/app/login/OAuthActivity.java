package com.ivan.github.app.login;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.design.widget.LoadingView;
import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.web.BaseWebChromeClient;
import com.ivan.github.web.BaseWebViewClient;

/**
 * Adapter for Splash
 *
 * @author  Ivan at 2020-10-12  22:09
 * @version v1.0
 * @since   v0.1.0
 */
public class OAuthActivity extends BaseActivity {

    private LoadingView mLoadingView;
    private WebView mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_oauth;
    }

    @Override
    protected void onPreCreateView() {
        super.onPreCreateView();
        setNavigationBarColor(R.color.colorPrimaryDark);
    }

    private void initWindowParams() {
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        params.width = point.x;
        params.height = (int) (point.y * 0.95);
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        initWindowParams();
        mLoadingView = findViewById(R.id.lv_loading);
        mWebView = findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        mWebView.setWebViewClient(new OAuthWebViewClient());
        mWebView.setWebChromeClient(new BaseWebChromeClient());
        mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id=08d9cad09d2e1745edb4");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoadingView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoadingView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.popup_out);
    }

    public void cancel(View view) {
        finish();
    }

    private class OAuthWebViewClient extends BaseWebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoadingView.start();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.stop();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, Uri uri) {
            String scheme = uri.getScheme();
            if ("github".equals(scheme)) {
                handleOAuth(uri);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, uri);
        }
    }

    private void handleOAuth(Uri uri) {
        if ("/oauth/redirect".equals(uri.getPath())) {
            String code = uri.getQueryParameter("code");
            

            Toast.makeText(OAuthActivity.this, "login success:"+ code, Toast.LENGTH_LONG).show();
            setResult(LoginConsts.RESULT_CODE_OAUTH_SUCCESS);
            finish();
        } else {
            String err = uri.getQueryParameter("");
            if (TextUtils.isEmpty(err)) {
                err = "login failed: " + uri;
            }
            Toast.makeText(OAuthActivity.this, err, Toast.LENGTH_LONG).show();
        }
    }

}