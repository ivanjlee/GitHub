package com.ivan.github;

import android.app.Application;

import com.github.utils.L;
import com.ivan.github.core.perf.CrashHandler;
import com.ivan.github.core.perf.DefaultCrashReporter;
import com.ivan.github.core.perf.PerformanceProvider;

/**
 * Custom Application
 *
 * @author  Ivan at 2016-12-11  22:57
 * @version v0.1
 * @since   v0.1
 */

public class GitHubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GitHub.init(this);
        L.setDebugLog(BuildConfig.DEBUG, L.VERBOSE);
        initCrashHandler();
    }

    private void initCrashHandler() {
        if (BuildConfig.DEBUG) {
            CrashHandler handler = CrashHandler.getInstance();
            handler.init(this);
            handler.setReporter(PerformanceProvider.provideCrashReporter());
        }
    }
}
