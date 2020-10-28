package com.ivan.github.app.feed.mvp;

import com.ivan.github.app.feed.model.Event;

import retrofit2.Call;

/**
 * com.ivan.github.app.events.mvp.IFeedDataStore
 *
 * @author  Ivan on 2019-12-18
 * @version v0.1
 * @since   v1.0
 **/
public interface IFeedDataStore {

    Call<Event[]> listUserEvents(int page);

}
