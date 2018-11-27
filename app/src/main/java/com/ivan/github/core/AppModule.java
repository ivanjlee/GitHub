package com.ivan.github.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.ivan.github.GitHubApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module to hold application
 *
 * @author  Ivan on 2018-11-17 00:00.
 * @version v0.1
 * @since   v1.0
 */
@Module
public class AppModule {

    private GitHubApplication mApplication;

    public AppModule(GitHubApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    GitHubApplication provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providePreference() {
        return mApplication.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return mApplication.getResources();
    }
}
