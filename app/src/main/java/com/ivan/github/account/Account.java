package com.ivan.github.account;

/**
 * Singleton, to save account information
 *
 * @author  lijun on 2017/10/6 01:12.
 * @version v0.1
 * @since   v1.0
 */

public class Account {

    private volatile static Account sInstance;

    private User mUser;
    private String mToken;

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

    public void init(User user, String token) {
        this.mUser = user;
        this.mToken = token;
    }

    public User getUser() {
        return mUser;
    }

    public String getToken() {
        return mToken;
    }
}