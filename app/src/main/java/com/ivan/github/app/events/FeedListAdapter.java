package com.ivan.github.app.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.design.widget.BaseRecyclerViewAdapter;
import com.ivan.github.app.events.model.Event;

/**
 * FeedListAdapter
 *
 * @author  Ivan on 2019-04-24 22:02.
 * @version v0.1
 * @since   v1.0
 */
public class FeedListAdapter extends BaseRecyclerViewAdapter<FeedListAdapter.FeedViewHolder, Event> {


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new FeedViewHolder(null);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i, Event event) {

    }


    static class FeedViewHolder extends RecyclerView.ViewHolder {

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
