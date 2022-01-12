package com.ivan.github.app.feed.model;

import com.ivan.github.app.feed.model.entity.Event;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * com.ivan.github.app.events.mvp.IFeedDataStore
 *
 * @author  Ivan on 2019-12-18
 * @version v0.1
 * @since   v1.0
 **/
public interface IFeedDataStore {

    int PAGE_SIZE = 30;

    Observable<List<Event>> listUserEvents(int page);

    Observable<List<Event>> listPublicEvents(int page);

}
