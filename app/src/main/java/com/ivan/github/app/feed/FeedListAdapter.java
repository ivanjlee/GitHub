package com.ivan.github.app.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.github.design.widget.BaseRecyclerViewAdapter;
import com.ivan.github.app.feed.model.Event;

/**
 * FeedListAdapter
 *
 * @author  Ivan on 2019-04-24 22:02.
 * @version v0.1
 * @since   v1.0
 */
public class FeedListAdapter extends BaseRecyclerViewAdapter<FeedViewHolder, Event> {

    public FeedListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return FeedViewHolderFactory.create(getContext(), viewGroup, getData(i));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i, Event event) {
        feedViewHolder.bindView(event);
    }

}
