package com.ivan.github.account;

import android.text.TextUtils;

import com.ivan.github.GitHub;
import com.ivan.github.account.model.User;

/**
 * Singleton, to save account information
 *
 * @author  Ivan on 2017/10/6 01:12.
 * @version v0.1
 * @since   v0.1.0
 */

public class Account {

    private static final String USER_DETAIL = "user_detail";
    private static final String AUTH_KEY = "auth_key";

    private volatile static Account sInstance;

    private User mUser;
    private String mAuthorization;

    public static Account getInstance() {
        if (sInstance == null) {
            synchronized (Account.class) {
                if (sInstance == null) {
                    sInstance = new Account();
                }
            }
        }
        return sInstance;
    }

    public void init(User user, String auth) {
        this.mUser = user;
        this.mAuthorization = auth;
        GitHub.appComponent().secureSharedPreference().edit().putString(AUTH_KEY, auth).apply();
    }

    public void updateAuth(String auth) {
        this.mAuthorization = auth;
        GitHub.appComponent().secureSharedPreference().edit().putString(AUTH_KEY, auth).apply();
    }

    public User getUser() {
        return mUser;
    }

    public String getAuthorization() {
        if (TextUtils.isEmpty(mAuthorization)) {
            mAuthorization = GitHub.appComponent().secureSharedPreference().getString(AUTH_KEY, "");
        }
        return mAuthorization;
    }

    public void saveUser() {
        if (mUser != null) {
            String user = GitHub.appComponent().gson().toJson(mUser);
            GitHub.appComponent().secureSharedPreference().edit().putString(USER_DETAIL, user).apply();
        }
    }

    public void loadUser() {
        String user = GitHub.appComponent().secureSharedPreference().getString(USER_DETAIL, null);
        if (!TextUtils.isEmpty(user)) {
            mUser = GitHub.appComponent().gson().fromJson(user, User.class);
            mAuthorization = GitHub.appComponent().secureSharedPreference().getString(AUTH_KEY, "");
        }
    }

    public void logout() {
        mUser = null;
        mAuthorization = null;
        GitHub.appComponent()
                .secureSharedPreference()
                .edit()
                .putString(USER_DETAIL, null)
                .putString(AUTH_KEY, null)
                .commit();
    }
}