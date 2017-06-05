package com.ivan.github.app;

import android.app.Application;

import com.ivan.github.BuildConfig;

import github.utils.L;

/**
 * Custom Application
 *
 * @author  Ivan at 2016-12-11  22:57
 * @version v0.1
 * @since   v0.1
 */

public class GitHubApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        L.setDebugLog(L.ASSERT);
        App.init(this);
        initCrashHandler();
    }

    private void initCrashHandler() {
//        if (BuildConfig.DEBUG) {
            CrashHandler handler = CrashHandler.getInstance();
            handler.init(this);
            handler.setReportToLocal(true);
//        }
    }
}
