package com.ivan.github;

import android.app.Application;

import com.ivan.github.core.init.AsyncAppInitializer;

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
        AsyncAppInitializer.getInstance(this).asyncInit();
    }
}
