package com.ivan.github.core.net;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.adapter.rxjava3.Result;

/**
 * com.ivan.github.core.net.TransformerHelper
 *
 * @author Ivan J. Lee on 2020-10-27 00:06
 * @version v0.1
 * @since v1.0
 **/
public class TransformerHelper {

    /**
     * transformer to switch thread for network
     * @param <T> resp
     * @return transformer
     */
    public static <T> ObservableTransformer<T, T> schedulers() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * transform Result<R> to R
     *
     * @param <R> response data
     * @return response date
     */
    public static <R> ObservableTransformer<Result<R>, R> result() {
        return upstream -> upstream.flatMap((Function<Result<R>, ObservableSource<R>>) result -> {
            if (result.isError()) {
                return Observable.error(new RuntimeException(result.error().getLocalizedMessage()));
            } else if (result.response() == null || result.response().body() == null) {
                return Observable.error(new RuntimeException("no data"));
            } else {
                return Observable.just(result.response().body());
            }
        });
    }
}
