package com.ivan.github.app.events.mvp;

import android.text.TextUtils;

import com.github.utils.CollectionUtils;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.core.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Event Presenter
 *
 * @author  Ivan J. Lee on 2019-04-22 22:42.
 * @version v0.1
 * @since   v1.0
 */
public class FeedPresenter extends BasePresenter<FeedContract.View, FeedModel>
        implements FeedContract.Presenter {

    private List<Event> mData = new ArrayList<>(FeedContract.pageSize);

    public FeedPresenter(FeedContract.View mView) {
        super(mView);
    }

    @Override
    protected FeedModel createModel(BasePresenter presenter) {
        return new FeedModel(this);
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
        getModel().listUserEvents(page);
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
        getModel().listUserEvents(0);
    }

    @Override
    public void loadMore() {
        int index = mData.size() / FeedContract.pageSize;
        if (mData.size() % FeedContract.pageSize == 0) { //not the last page
            getModel().listUserEvents(index);
        }
    }

}
