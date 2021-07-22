package com.ivan.github.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.ivan.github.BuildConfig;
import com.ivan.github.core.perf.CrashHandler;
import com.ivan.github.core.perf.PerformanceProvider;

import java.util.Arrays;
import java.util.List;

/**
 * com.ivan.github.init.CrashReporterInit
 *
 * @author Ivan J. Lee on 2021-07-20 00:11
 * @version v0.1
 * @since v1.0
 **/
public class CrashReporterInit extends AbsInitializer<CrashHandler> {

    @NonNull
    @Override
    public String getTag() {
        return "CrashReporter";
    }

    @NonNull
    @Override
    public CrashHandler onCreate(@NonNull Context context) {
        CrashHandler handler = CrashHandler.getInstance();
        if (BuildConfig.DEBUG) {
            handler.init(context);
            handler.setReporter(PerformanceProvider.provideCrashReporter());
        }
        return handler;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Arrays.asList(LogInit.class, AppInit.class);
    }
}
