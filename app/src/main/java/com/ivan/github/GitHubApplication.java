package com.ivan.github;

import android.app.Application;

import com.github.log.Logan;
import com.github.log.LogcatFactory;
import com.github.utils.L;
import com.ivan.github.core.perf.CrashHandler;
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
        initLog();
        initCrashHandler();
    }

    private void initLog() {
        L.setDebugLog(BuildConfig.DEBUG, L.VERBOSE);
        if (BuildConfig.DEBUG) {
            Logan.init(new LogcatFactory());
        }
    }

    private void initCrashHandler() {
        if (BuildConfig.DEBUG) {
            CrashHandler handler = CrashHandler.getInstance();
            handler.init(this);
            handler.setReporter(PerformanceProvider.provideCrashReporter());
        }
    }
}
