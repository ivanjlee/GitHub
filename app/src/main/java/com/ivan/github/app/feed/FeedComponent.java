package com.ivan.github.app.feed;

import com.ivan.github.app.feed.presenter.FeedPresenter;
import com.ivan.github.app.feed.ui.FeedFragment;

import dagger.Component;

/**
 * Event Component
 *
 * @author  Ivan J. Lee on 2019-04-22 22:46.
 * @version v0.1
 * @since   v1.0
 */
@Component(modules = {FeedModule.class})
public interface FeedComponent {

    void inject(FeedFragment fragment);

    void inject(FeedPresenter presenter);
}
