package com.github.design.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Load more and refresh listener
 *
 * @author lijun on 2019-04-21 12:23.
 * @version v0.1
 * @since v1.0
 */
public abstract class LoadListener {

    public void onRefresh(){}

    public void onLoadMore(RecyclerView recyclerView){}
}
