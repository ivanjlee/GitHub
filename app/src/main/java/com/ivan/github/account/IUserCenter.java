package com.ivan.github.account;

import com.ivan.github.account.model.User;

public interface IUserCenter {

    boolean isLogin();

    User getUser();

    String getToken();

}
