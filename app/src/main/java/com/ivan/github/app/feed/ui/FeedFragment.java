package com.ivan.github.app.feed.ui;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.design.widget.LoadListener;
import com.github.design.widget.PLRecyclerView;
import com.ivan.github.R;
import com.ivan.github.app.feed.DaggerFeedComponent;
import com.ivan.github.app.feed.FeedModule;
import com.ivan.github.app.feed.model.entity.Event;
import com.ivan.github.app.feed.FeedContract;
import com.ivan.github.app.feed.presenter.FeedPresenter;
import com.ivan.github.core.mvp.BaseMvpFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * FeedFragment
 *
 * @author Ivan J. Lee
 */
public class FeedFragment extends BaseMvpFragment<FeedContract.Presenter> implements FeedContract.View {

    private PLRecyclerView mRecyclerView;
    private FeedListAdapter mAdapter;
    private TextView mTvEmpty;

    @Inject
    FeedPresenter mPresenter;

    public FeedFragment() {
        // empty public constructor
        inject();
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    private void inject() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onViewCreated(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.pl_recycler_view);
        mAdapter = new FeedListAdapter(getContext());
        mTvEmpty = rootView.findViewById(R.id.tv_empty_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setLoadListener(new LoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }

            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                super.onLoadMore(recyclerView);
                mPresenter.loadMore();
            }
        });
        mPresenter.listUserEvents(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }

    @Override
    public void updateList(List<Event> list) {
        showNormalView();
        int size = mAdapter.getItemCount();
        mRecyclerView.setRefreshing(false);
        mAdapter.appendData(list);
        mAdapter.notifyItemInserted(size);
    }

    @Override
    public void showEmptyView() {
        mTvEmpty.setText(R.string.feed_empty_text);
        mTvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage(int code, String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_LONG).show();
        showErrorView(getString(R.string.error) + "#" + code, error);
        if (mAdapter.getItemCount() > 0) {
            Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_LONG).show();
        } else {
            mTvEmpty.setText(error);
            mTvEmpty.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showEnd() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public FeedContract.Presenter getPresenter() {
        return mPresenter;
    }
}
