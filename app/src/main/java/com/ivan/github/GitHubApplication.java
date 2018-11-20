package com.ivan.github;

import android.app.Application;

import com.ivan.github.app.App;

import com.github.utils.L;
import com.ivan.github.app.CrashHandler;
import com.ivan.github.core.AppComponent;
import com.ivan.github.core.AppModule;
import com.ivan.github.core.DaggerAppComponent;
import com.ivan.github.core.net.NetModule;

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
        GitHub.init(this);
        L.setDebugLog(BuildConfig.DEBUG, L.VERBOSE);
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
