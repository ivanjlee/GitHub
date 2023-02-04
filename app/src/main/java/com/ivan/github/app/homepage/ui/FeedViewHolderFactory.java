package com.ivan.github.app.homepage.ui;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.ViewGroup;

import com.ivan.github.R;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.app.homepage.model.entity.event.EventType;
import com.ivan.github.app.homepage.ui.viewholder.DefaultViewHolder;
import com.ivan.github.app.homepage.ui.viewholder.DeleteEventViewHolder;

/**
 * com.ivan.github.app.events.FeedViewHolderFactory
 *
 * @author Ivan on 2019-12-24
 * @version v0.1
 * @since v1.0
 **/
public class FeedViewHolderFactory {

    public static @NonNull FeedViewHolder create(Context context, ViewGroup parent, Event event) {
        switch (event.getType()) {
            case EventType.DELETE_EVENT:
                return DeleteEventViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_delete);
            default:
                return DefaultViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_default);
        }
    }

}
