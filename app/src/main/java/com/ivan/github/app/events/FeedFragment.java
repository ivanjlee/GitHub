package com.ivan.github.app.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.design.widget.LoadListener;
import com.github.design.widget.PLRecyclerView;
import com.ivan.github.R;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.app.events.mvp.FeedContract;
import com.ivan.github.app.events.mvp.FeedPresenter;
import com.ivan.github.core.mvp.IBaseMvpFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * FeedFragment
 *
 * @author Ivan J. Lee
 */
public class FeedFragment extends IBaseMvpFragment<FeedContract.Presenter> implements FeedContract.View {

    private PLRecyclerView mRecyclerView;

    @Inject
    FeedPresenter mPresenter;

    public FeedFragment() {
        // Required empty public constructor
        createPresenter();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        inject();
        initView(rootView);
        mPresenter.listUserEvents(0);
        return rootView;
    }

    private void inject() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule(this))
                .build()
                .inject(this);
    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.pl_recycler_view);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.stop();
    }

    @Override
    public void updateList(List<Event> list) {
        Snackbar.make(mRecyclerView, "get " + list.size() + " events", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorPage(String error) {
        Snackbar.make(mRecyclerView, error + " events", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEnd() {

    }

    @Override
    public FeedContract.Presenter createPresenter() {
        return mPresenter;
    }
}
