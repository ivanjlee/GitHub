package com.ivan.github.app.feed.mvp;

import com.ivan.github.account.Account;
import com.ivan.github.api.EventService;
import com.ivan.github.app.feed.model.Event;
import com.ivan.github.core.net.HttpClient;

import retrofit2.Call;

/**
 * Event Model
 *
 * @author  Ivan J. Lee on 2019-04-22 22:56.
 * @version v0.1
 * @since   v1.0
 */
public class FeedRepository implements IFeedDataStore {

    @Override
    public Call<Event[]> listUserEvents(int page) {
        return HttpClient.service(EventService.class)
                .listUserEvents(Account.getInstance().getUser().getLogin(), page);
    }
}
