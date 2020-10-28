package com.ivan.github.core.net;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
}
