package com.ivan.github.app.events;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.design.widget.LoadListener;
import com.github.design.widget.PLRecyclerView;
import com.ivan.github.R;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.app.events.mvp.FeedContract;
import com.ivan.github.app.events.mvp.FeedPresenter;
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
    protected void initView(View rootView) {
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
        mRecyclerView.setRefreshing(false);
        mAdapter.appendData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        mTvEmpty.setText(R.string.feed_empty_text);
        mTvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage(String error) {
        Snackbar.make(mRecyclerView, error + " events", Snackbar.LENGTH_LONG).show();
        showErrorView();
        if (mAdapter.getItemCount() > 0) {
            Snackbar.make(mRecyclerView, error + " events", Snackbar.LENGTH_LONG).show();
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
