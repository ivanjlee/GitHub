package com.ivan.github;

import com.ivan.github.core.AppComponent;
import com.ivan.github.core.AppModule;
import com.ivan.github.core.DaggerAppComponent;
import com.ivan.github.core.net.NetConfig;
import com.ivan.github.core.net.NetModule;

/**
 * a Global single Class
 *
 * @author  Ivan on 2018-11-17 00:18.
 * @version v0.1
 * @since   v1.0
 */
public final class GitHub {

    private static GitHub mGitHub;
    private static volatile AppComponent sDaggerAppComponent;

    private GitHub() {
        throw new IllegalArgumentException("not allowed");
    }

    private static GitHub getInstance(GitHubApplication application) {
        if (mGitHub == null) {
            synchronized (GitHub.class) {
                if (mGitHub == null) {
                    mGitHub = new GitHub(application);
                }
            }
        }
        return mGitHub;
    }

    private GitHub(GitHubApplication application) {
        sDaggerAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .netModule(new NetModule(NetConfig.defaultConfig()))
                .build();
    }

    static void init(GitHubApplication application) {
        getInstance(application);

    }

    public static AppComponent appComponent() {
        return sDaggerAppComponent;
    }
}
