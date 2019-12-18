package com.ivan.github.app.events.mvp;

import com.ivan.github.account.Account;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.net.HttpClient;

import java.util.List;

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
    public Call<List<Event>> listUserEvents(int page) {
        return HttpClient.eventService()
                .listUserEvents(Account.getInstance().getUser().getName(), page);
    }
}
