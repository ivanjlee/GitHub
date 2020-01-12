package com.ivan.github.app.events.mvp;

import android.text.TextUtils;

import com.github.log.Logan;
import com.github.utils.CollectionUtils;
import com.ivan.github.app.events.DaggerFeedComponent;
import com.ivan.github.app.events.FeedModule;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.mvp.BasePresenter;
import com.ivan.github.core.net.ApiCallback;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String TAG = "FeedPresenter";

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

    }

    @Override
    public void stop() {
        Logan.d(TAG, "stop presenter");
    }

    @Override
    public void listUserEvents(int page) {
        getView().showLoading();
        mDataStore.listUserEvents(page)
                .enqueue(new ApiCallback<Event[]>() {
                    @Override
                    public void onSuccess(Event[] response) {
                        if (getView().isAlive()) {
                            getView().dismissLoading();
                            onGetUserEvents(Arrays.asList(response));
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg, Throwable throwable) {
                        if (getView().isAlive()) {
                            getView().dismissLoading();
                            onGetUserEventError(msg);
                        }
                    }
                });
    }

    private void onGetUserEvents(List<Event> list) {
        if (CollectionUtils.isEmpty(list)) {
            if (mData.isEmpty()) {
                getView().showEmptyView();
            } else {
                getView().showEnd();
            }
        } else if (mData.isEmpty()) {
            mData.addAll(list);
            getView().updateList(list);
        } else {
            Event e1 = CollectionUtils.getLast(mData);
            Event e2 = CollectionUtils.getLast(list);
            if (e1 == null || e2 == null || !TextUtils.equals(e1.getId(), e2.getId())) {
                mData.addAll(list);
                getView().updateList(list);
            }
        }
    }

    private void onGetUserEventError(String msg) {
        getView().showErrorPage(msg);
    }

    @Override
    public void refresh() {
        mData.clear();
        listUserEvents(0);
    }

    @Override
    public void loadMore() {
        int index = mData.size() / FeedContract.pageSize;
        if (mData.size() % FeedContract.pageSize == 0) { //not the last page
            listUserEvents(index);
        }
    }

}
