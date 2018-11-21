package com.ivan.github.core;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.ivan.github.GitHubApplication;
import com.ivan.github.core.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * AppComponent
 *
 * @author  Ivan on 2018-11-16 23:47.
 * @version v0.1
 * @since   v1.0
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    GitHubApplication application();

    SharedPreferences preference();

    Resources resources();

    Retrofit retrofit();

}
