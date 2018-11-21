package com.ivan.github.core.net;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * request and response log
 *
 * @author  Ivan on 2018-11-22 00:01.
 * @version v0.1
 * @since   v1.0
 */
public class HttpHeaderInterceptor implements Interceptor  {


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(request);
    }
}
