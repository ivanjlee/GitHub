package com.ivan.github.app.events.mvp;

import com.ivan.github.app.events.model.Event;

import java.util.List;

import retrofit2.Call;

/**
 * com.ivan.github.app.events.mvp.IFeedDataStore
 *
 * @author  lijun on 2019-12-18
 * @version v0.1
 * @since   v1.0
 **/
public interface IFeedDataStore {

    Call<List<Event>> listUserEvents(int page);

}
