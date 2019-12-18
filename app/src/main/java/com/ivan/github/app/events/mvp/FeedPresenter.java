package com.ivan.github.app.events.mvp;

import android.text.TextUtils;

import com.github.utils.CollectionUtils;
import com.ivan.github.app.events.DaggerFeedComponent;
import com.ivan.github.app.events.FeedModule;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.mvp.BasePresenter;
import com.ivan.github.core.net.ApiCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Event Presenter
 *
 * @author  Ivan J. Lee on 2019-04-22 22:42.
 * @version v0.1
 * @since   v1.0
 */
public class FeedPresenter extends BasePresenter<FeedContract.View>
        implements FeedContract.Presenter {

    @Inject
    IFeedDataStore mDataStore;

    private List<Event> mData = new ArrayList<>(FeedContract.pageSize);

    public FeedPresenter(FeedContract.View mView) {
        super(mView);
        inject();
    }

    protected void inject() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule())
                .build()
                .inject(this);
    }

    @Override
    public void start() {
        listUserEvents(0);
    }

    @Override
    public void stop() {

    }


    @Override
    public void listUserEvents(int page) {
        mDataStore.listUserEvents(page)
        .enqueue(new ApiCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> response) {
                getView().updateList(response);
            }

            @Override
            public void onFailure(int code, String msg, Throwable throwable) {
                getView().showErrorPage(msg);
            }
        });
    }

    @Override
    public void onGetUserEvents(List<Event> list) {
        if (CollectionUtils.isEmpty(list)) {
            if (mData.isEmpty()) {
                getView().showEmptyView();
            } else {
                getView().showEnd();
            }
        } else {
            Event e1 = mData.get(mData.size() - 1);
            Event e2 = list.get(mData.size() - 1);
            if (!TextUtils.equals(e1.getId(), e2.getId())) {
                mData.addAll(list);
                getView().updateList(list);
            }
        }
    }

    @Override
    public void onGetUserEventError(String msg) {
        getView().showErrorPage(msg);
    }

    @Override
    public void refresh() {
        mData.clear();
        mDataStore.listUserEvents(0);
    }

    @Override
    public void loadMore() {
        int index = mData.size() / FeedContract.pageSize;
        if (mData.size() % FeedContract.pageSize == 0) { //not the last page
            mDataStore.listUserEvents(index);
        }
    }

}
