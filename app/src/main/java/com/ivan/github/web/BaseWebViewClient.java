package com.ivan.github.web;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.utils.L;

/**
 * WebViewClient for the WebViews
 *
 * @author  Ivan J. Lee on 2017/10/5 20:35.
 * @version v0.1
 * @since   v1.0
 */

public class BaseWebViewClient extends WebViewClient {

    private static final String TAG = "WebView";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            L.v(TAG, request.getUrl().toString());
        }
        return super.shouldOverrideUrlLoading(view, request);
    }
}
