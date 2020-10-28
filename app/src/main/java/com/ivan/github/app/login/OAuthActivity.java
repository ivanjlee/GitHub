package com.ivan.github.app.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.design.widget.EmptyView;
import com.github.design.widget.LoadingView;
import com.github.log.Logan;
import com.ivan.github.GitHub;
import com.ivan.github.R;
import com.ivan.github.account.Account;
import com.ivan.github.app.AppSettings;
import com.ivan.github.app.ToolbarActivity;
import com.ivan.github.app.login.model.OAuthReq;
import com.ivan.github.app.login.model.OAuthResp;
import com.ivan.github.app.main.MainActivity;
import com.ivan.github.core.net.TransformerHelper;
import com.ivan.github.web.BaseWebChromeClient;
import com.ivan.github.web.BaseWebViewClient;

import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.HttpUrl;

/**
 * Adapter for Splash
 *
 * @author  Ivan at 2020-10-12  22:09
 * @version v1.0
 * @since   v0.1.0
 */
public class OAuthActivity extends ToolbarActivity implements View.OnClickListener {

    private static final String TAG = "OAuthActivity";

    private EmptyView mEmptyView;
    private LoadingView mLoadingView;
    private WebView mWebView;

    private String mUrl;

    private static final String clientId = "08d9cad09d2e1745edb4";
    private static final String clientSecret = "3943633750719013ae8208f6f001951566328218";

    private static final String sOAuthPath = "/oauth/redirect";

    @Override
    protected int getContentView() {
        return R.layout.activity_oauth;
    }

    @Override
    protected void onPreCreateView() {
        super.onPreCreateView();
        setNavigationBarColor(R.color.colorPrimaryDark);
    }

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        mEmptyView = findViewById(R.id.empty_view);
        mEmptyView.setOnClickListener(this);
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
        mWebView.setWebViewClient(new OAuthWebViewClient(new OAuthRedirectHandler()));
        mWebView.setWebChromeClient(new BaseWebChromeClient());
        mUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("github.com")
                .addPathSegments("login/oauth/authorize")
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("scope", "user,repo,workflow")
                .build()
                .toString();
        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oauth_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_refresh) {
            showLoading();
            mEmptyView.setVisibility(View.GONE);
            mWebView.loadUrl(mUrl);
        }
        return super.onOptionsItemSelected(item);
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
            clearWebViewCache();
            super.onBackPressed();
        }
    }

    public void clearWebViewCache() {
        mWebView.clearCache(true);
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeSessionCookie();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }
        WebStorage.getInstance().deleteAllData();
    }

    private void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.start();
        }
    }

    private void dismissLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.stop();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.empty_view) {
            showLoading();
            mEmptyView.setVisibility(View.GONE);
            mWebView.reload();
        }
    }

    private class OAuthWebViewClient extends BaseWebViewClient {

        private final AbsUriHandler handler;

        public OAuthWebViewClient(AbsUriHandler handler) {
            this.handler = handler;
        }

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
            if (handler != null && handler.handleInternal(uri)) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, uri);
        }
    }

    private class OAuthRedirectHandler extends AbsUriHandler {

        public OAuthRedirectHandler() {
            next(new ErrorHandler());
        }

        @Override
        public boolean shouldHandle(Uri uri) {
            return super.shouldHandle(uri) &&
                    sOAuthPath.equals(uri.getPath());
        }

        @Override
        protected void onHandle(Uri uri) {
            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(OAuthActivity.this, error, Toast.LENGTH_SHORT).show();
                Logan.e(TAG, "login failed: " + error);
            } else {
                final OAuthReq req = new OAuthReq();
                req.client_id = clientId;
                req.client_secret = clientSecret;
                req.code = code;
//                req.redirect_uri = sOAuthLoginPath;
                req.state = UUID.randomUUID().toString();
                final OAuthResp[] auth = new OAuthResp[1];
                Disposable d = GitHub.appComponent()
                        .githubService()
                        .oauth("https://github.com/login/oauth/access_token", req)
                        .doOnSubscribe(disposable -> showLoading())
                        .doOnComplete(OAuthActivity.this::dismissLoading)
                        .doOnDispose(OAuthActivity.this::dismissLoading)
                        .flatMap(oAuthResp -> {
                            auth[0] = oAuthResp;
                            return GitHub.appComponent()
                                    .githubService()
                                    .getUser(oAuthResp.access_token);
                        })
                        .compose(TransformerHelper.schedulers())
                        .subscribe(user -> {
                            AppSettings.setFirstLogin(false);
                            Account.getInstance().init(user, auth[0].access_token);
                            Account.getInstance().saveUser();
                            startActivity(new Intent(OAuthActivity.this, MainActivity.class));
                            setResult(LoginConsts.RESULT_CODE_OAUTH_SUCCESS);
                            finish();
                        }, throwable -> {
                            showToast(throwable.getLocalizedMessage());
                            mEmptyView.setMessage(String.format("%s\n%s", throwable.getLocalizedMessage(),
                                    getString(R.string.tap_to_retry)));
                            mEmptyView.setVisibility(View.VISIBLE);
                        });
                addDisposable(d);
            }
        }
    }

    private class ErrorHandler extends AbsUriHandler {

        @Override
        protected void onHandle(Uri uri) {
            String err = uri.getQueryParameter("error");
            if (TextUtils.isEmpty(err)) {
                err = "login failed";
            }
            Toast.makeText(OAuthActivity.this, err, Toast.LENGTH_LONG).show();
        }
    }

}