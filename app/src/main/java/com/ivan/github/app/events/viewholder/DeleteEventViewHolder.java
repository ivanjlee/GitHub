package com.ivan.github.app.events.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ivan.github.R;
import com.ivan.github.app.events.FeedViewHolder;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.app.events.model.payload.DeleteEventPayload;
import com.ivan.github.util.DateFormatUtils;

/**
 * com.ivan.github.app.events.viewholder.DeleteEventViewHolder
 *
 * @author Ivan J. Lee on 2020-01-01 23:15
 * @version v0.1
 * @since v1.0
 **/
public class DeleteEventViewHolder extends FeedViewHolder {

    private ImageView mIvAvatar;
    private TextView mTvUsername;
    private TextView mTvAction;
    private TextView mTvTime;
    private TextView mTvRef;
    private TextView mTvRepository;

    public static DeleteEventViewHolder newInstance(Context context, ViewGroup parent, int id) {
        return new DeleteEventViewHolder(context, parent, id);
    }

    public DeleteEventViewHolder(Context context, ViewGroup parent, int id) {
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
        DeleteEventPayload payload = (DeleteEventPayload) event.getPayload();
        Glide.with(getContext())
                .load(event.getActor().avatarUrl)
                .into(mIvAvatar);
        mTvUsername.setText(event.getActor().getName());
        if (DeleteEventPayload.REF_TYPE_BRANCH.equals(payload.getRefType())) {
            mTvAction.setText(R.string.feed_delete_branch);
        } else if (DeleteEventPayload.REF_TYPE_TAG.equals(payload.getRefType())) {
            mTvAction.setText(R.string.feed_delete_tag);
        } else {
            mTvAction.setText(R.string.feed_delete);
        }
        mTvTime.setText(DateFormatUtils.getTimeSpan(event.getCreatedAt()));
        mTvRef.setText(payload.getRef());
        mTvRepository.setText(payload.getRepository().getFullName());
    }
}
