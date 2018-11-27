package com.ivan.github.core.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ivan.github.account.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * authorization
 *
 * @author  Ivan on 2018-11-21 23:34.
 * @version v0.1
 * @since   v0.1.0
 */
public class AuthorizationInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String auth = Account.getInstance().getAuthorization();
        String authorization = request.header("Authorization");
        if (!TextUtils.isEmpty(auth) && TextUtils.isEmpty(authorization)) {
            request = request.newBuilder()
                    .addHeader("Authorization", auth)
                    .build();
        }
        return chain.proceed(request);
    }
}
