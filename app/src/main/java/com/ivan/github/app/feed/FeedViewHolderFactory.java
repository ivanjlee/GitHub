package com.ivan.github.app.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ivan.github.R;
import com.ivan.github.app.feed.model.Event;
import com.ivan.github.app.feed.model.EventType;
import com.ivan.github.app.feed.viewholder.DeleteEventViewHolder;
import com.ivan.github.util.DateFormatUtils;

/**
 * com.ivan.github.app.events.FeedViewHolderFactory
 *
 * @author Ivan on 2019-12-24
 * @version v0.1
 * @since v1.0
 **/
public class FeedViewHolderFactory {

    private static class DefaultViewHolder extends FeedViewHolder {

        private ImageView mIvAvatar;
        private TextView mTvUsername;
        private TextView mTvAction;
        private TextView mTvTime;
        private TextView mTvRef;
        private TextView mTvRepository;

        static DefaultViewHolder newInstance(Context context, ViewGroup parent, int id) {
            return new DefaultViewHolder(context, parent, id);
        }

        DefaultViewHolder(Context context, ViewGroup parent, int id) {
            super(context, parent, id);
        }

        @Override
        public void initView(View itemView) {
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mTvUsername = itemView.findViewById(R.id.tv_username);
            mTvAction = itemView.findViewById(R.id.tv_action);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvRef = itemView.findViewById(R.id.tv_ref);
            mTvRepository = itemView.findViewById(R.id.tv_repository);
        }

        @Override
        public void bindView(Event event) {
            Glide.with(getContext())
                    .load(event.getActor().avatarUrl)
                    .into(mIvAvatar);
            mTvUsername.setText(event.getActor().getDisplayLogin());
            mTvAction.setText(R.string.feed_action_default);
            mTvTime.setText(DateFormatUtils.getTimeSpan(event.getCreatedAt()));
            mTvRepository.setText(event.getRepo().getFullName());
        }
    }

    public static @NonNull FeedViewHolder create(Context context, ViewGroup parent, Event event) {
        if (EventType.DELETE_EVENT.equals(event.getType())) {
            return DeleteEventViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_delete);
        } else {
            return DefaultViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_default);
        }
    }

}
