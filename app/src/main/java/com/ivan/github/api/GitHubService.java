package com.ivan.github.api;

import com.ivan.github.account.Authorization;
import com.ivan.github.account.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * all requests
 *
 * @author  Ivan J. Lee on 2018-11-22 23:28.
 * @version v0.1
 * @since   v0.1.0
 */
public interface GitHubService {

    @GET("/authorizations")
    Call<List<Authorization>> listAuthorizations(@Header("Authorization") String auth);

    @GET("/user")
    Call<User> getAuthorizedUser(@Header("Authorization") String auth);

    @GET("/user")
    Call<User> getAuthorizedUser();
}
