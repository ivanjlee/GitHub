package com.ivan.github.app.events;

import com.ivan.github.app.events.mvp.FeedContract;
import com.ivan.github.app.events.mvp.FeedPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * EventModule
 *
 * @author  Ivan J. Lee on 2019-04-22 21:45.
 * @version v0.1
 * @since   v1.0
 */
@Module
public class FeedModule {

    @Provides
    public FeedContract.Presenter providePresenter(FeedContract.View view) {
        return new FeedPresenter(view);
    }

}