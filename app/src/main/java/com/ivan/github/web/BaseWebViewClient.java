package com.ivan.github.web;

import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.github.log.Logan;

/**
 * WebViewClient for the WebViews
 *
 * @author  Ivan J. Lee on 2017/10/5 20:35.
 * @version v0.1
 * @since   v1.0
 */

public class BaseWebViewClient extends WebViewClient {

    private static final String TAG = "WebView";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Logan.v(TAG, request.getUrl().toString());
        }
        return shouldOverrideUrlLoading(view, request.getUrl());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        return shouldOverrideUrlLoading(view, uri);
    }

    public boolean shouldOverrideUrlLoading(WebView view, Uri uri) {
        return false;
    }
}
