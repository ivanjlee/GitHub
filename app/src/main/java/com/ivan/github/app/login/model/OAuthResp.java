package com.ivan.github.app.login.model;

import com.github.annotations.NoProguard;

/**
 * com.ivan.github.app.login.model.LoginResp
 *
 * @author Ivan J. Lee on 2020-10-24 23:26
 * @version v0.1
 * @since v1.0
 **/
@NoProguard
public class OAuthResp {

    public String access_token;
    public String scope;
    public String token_type;
}
