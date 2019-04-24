package com.ivan.github.app.events.mvp;

import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.mvp.BaseView;
import com.ivan.github.core.mvp.IModel;
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

    int pageSize = 30;

    interface View extends BaseView<Presenter> {

        void updateList(List<Event> list);

        void showEmptyView();

        void showErrorPage(String error);

        void showEnd();

        void loading(String msg);

        void dismissLoading();
    }

    interface Presenter extends IPresenter {

        void listUserEvents(int page);

        void onGetUserEvents(List<Event> list);

        void onGetUserEventError(String msg);

        void refresh();

        void loadMore();
    }

    interface Model extends IModel {

        void listUserEvents(int page);
    }
}
