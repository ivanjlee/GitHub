package com.github.design.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.github.design.R;
import com.github.utils.L;

/**
 * RecyclerView with pull to refresh, load more, header and footer layout.
 *
 * @author  Ivan on 2019-04-01 00:11.
 * @version v0.1
 * @since   v1.0
 */
public class PLRecyclerView extends SwipeRefreshLayout {

    public static final String TAG = "PLRecyclerView";
    private static final int VISIBLE_THRESHOLD = 1;

    private RecyclerView mRecyclerView;
    private LoadListener mListener;
    private boolean isCanLoadMore;

    public PLRecyclerView(@NonNull Context context) {
        this(context, (AttributeSet)null);
    }

    public PLRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_plrecycler_view, this, true);
        mRecyclerView = view.findViewById(R.id.recycler_view);
//        this.addView(view);
        this.setOnRefreshListener(() -> {
            if (mRecyclerView !=null ) {
                mListener.onRefresh();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                if (layoutManager == null) {
                    L.w(TAG, "layoutAdapter is empty, can not load more!");
                    return;
                }
                if (!(layoutManager instanceof LinearLayoutManager)) {
                    L.w(TAG, "layoutAdapter is not LinearLayoutManager, cannot load more");
                    return;
                }
                if (isCanLoadMore && dy >= 0) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastVisibleItem + VISIBLE_THRESHOLD >= totalItemCount) {
                        mListener.onLoadMore(recyclerView);
                    }
                }
            }
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public RecyclerView getRealRecyclerView() {
        return mRecyclerView;
    }

    public LoadListener getLoadListener() {
        return mListener;
    }

    public void setLoadListener(LoadListener listener) {
        this.mListener = listener;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.isCanLoadMore = canLoadMore;
    }
}
