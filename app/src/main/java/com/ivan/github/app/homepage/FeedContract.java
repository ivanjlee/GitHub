package com.ivan.github.app.homepage;

import com.ivan.github.app.homepage.model.IFeedDataStore;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.core.mvp.IBaseStateView;
import com.ivan.github.core.mvp.IPresenter;

import java.util.List;

/**
 * Event Contract
 *
 * @author  Ivan J. Lee on 2019-04-22 22:20.
 * @version v0.1
 * @since   v1.0
 */
public interface FeedContract {

    int pageSize = IFeedDataStore.PAGE_SIZE;

    interface View extends IBaseStateView<Presenter> {

        void updateList(List<Event> list);

        void showEmptyView();

        void showErrorPage(int code, String error);

        void showEnd();
    }

    interface Presenter extends IPresenter<View> {

        void listUserEvents(int page);

        void refresh();

        void loadMore();
    }
}
