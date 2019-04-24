package com.ivan.github.app.events.mvp;

import com.ivan.github.account.Account;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.mvp.BaseModel;
import com.ivan.github.core.net.ApiCallback;
import com.ivan.github.core.net.HttpClient;

import java.util.List;

/**
 * Event Model
 *
 * @author  Ivan J. Lee on 2019-04-22 22:56.
 * @version v0.1
 * @since   v1.0
 */
public class FeedModel extends BaseModel<FeedPresenter> implements FeedContract.Model {

    public FeedModel(FeedPresenter feedPresenter) {
        super(feedPresenter);
    }

    @Override
    public void listUserEvents(int page) {
        HttpClient.eventService()
                .listUserEvents(Account.getInstance().getUser().getName(), page)
                .enqueue(new ApiCallback<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> response) {

                    }

                    @Override
                    public void onFailure(int code, String msg, Throwable throwable) {

                    }
                });
    }
}
