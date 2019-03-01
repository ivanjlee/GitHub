package com.ivan.github.app;

import com.ivan.github.BuildConfig;
import com.ivan.github.GitHub;

/**
 * global settings
 *
 * @author  Ivan on 2018-12-24 23:01.
 * @version v0.1
 * @since   v0.1.0
 */
public class AppSettings {

    private static final String PREFIX = BuildConfig.APPLICATION_ID;

    private static final String KEY_FIRST_LOGIN = PREFIX + ".first_login";

    public static void setFirstLogin(boolean firstLogin) {
        GitHub.appComponent().preference().edit().putBoolean(KEY_FIRST_LOGIN, firstLogin).apply();
    }

    public static boolean isFirstLogin() {
        return GitHub.appComponent().preference().getBoolean(KEY_FIRST_LOGIN, true);
    }
}
