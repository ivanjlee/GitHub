package com.ivan.github.app.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.design.widget.LoadListener;
import com.github.design.widget.PLRecyclerView;
import com.ivan.github.R;
import com.ivan.github.app.BaseFragment;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.app.events.mvp.FeedContract;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Ivan J. Lee
 */
public class FeedFragment extends BaseFragment implements FeedContract.View {

    private PLRecyclerView mRecyclerView;

    @Inject
    FeedContract.Presenter mPresenter;

    public FeedFragment() {
        // Required empty public constructor
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
        DaggerFeedComponent.create().inject(this);
    }

    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
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
        mPresenter.start();
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.stop();
    }

    @Override
    public void updateList(List<Event> list) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorPage(String error) {

    }

    @Override
    public void showEnd() {

    }

    @Override
    public void loading(String msg) {

    }

    @Override
    public void dismissLoading() {

    }
}
