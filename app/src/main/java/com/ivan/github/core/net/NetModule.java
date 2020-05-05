package com.ivan.github.core.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivan.github.api.EventService;
import com.ivan.github.api.GitHubService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * net module
 *
 * @author  Ivan on 2018-11-16 23:58.
 * @version v0.1
 * @since   v1.0
 */
@Module
public class NetModule {

    private NetConfig mNetConfig = NetConfig.defaultConfig();

    public NetModule() {
        this(NetConfig.defaultConfig());
    }

    public NetModule(NetConfig mNetConfig) {
        this.mNetConfig = mNetConfig;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mNetConfig.getUrl())
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.callTimeout(mNetConfig.getTimeout(), TimeUnit.MILLISECONDS)
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(mNetConfig.getLogLevel()))
                .addInterceptor(new HttpHeaderInterceptor());
        return client.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    GitHubService provideGitHubService() {
        return provideRetrofit().create(GitHubService.class);
    }

    @Provides
    @Singleton
    EventService provideEventService() {
        return provideRetrofit().create(EventService.class);
    }

}
