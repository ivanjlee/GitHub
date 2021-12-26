package com.ivan.github.core.mvp;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.design.LoadingDialog;
import com.ivan.github.R;
import com.ivan.github.app.BaseFragment;

/**
 * com.ivan.github.core.mvp
 *
 * @author  Ivan J. Lee on 2019-11-19
 * @version v0.1
 * @since   v1.0
 **/
public abstract class BaseMvpFragment<P extends IPresenter<?>> extends BaseFragment implements IBaseStateView<P> {

    protected static final int STATE_NORMAL = 1;
    protected static final int STATE_EMPTY = 2;
    protected static final int STATE_ERROR = 3;

    protected View mRootView;
    protected LoadingDialog mLoadingDialog;

    protected SparseArray<View> mStateViews = new SparseArray<>(3);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_base_fragment, container, false);
        if (getContext() != null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        initState();
        onViewCreated(mRootView);
        return mRootView;
    }

    protected void initState() {
        FrameLayout frameLayout = ((FrameLayout) mRootView);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, frameLayout, false);
        View errorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_error_view, frameLayout, false);
        View normalView = LayoutInflater.from(getContext()).inflate(getLayoutId(), frameLayout, false);
        mStateViews.put(STATE_EMPTY, emptyView);
        mStateViews.put(STATE_ERROR, errorView);
        mStateViews.put(STATE_NORMAL, normalView);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        normalView.setVisibility(View.VISIBLE);
        frameLayout.addView(emptyView);
        frameLayout.addView(errorView);
        frameLayout.addView(normalView);
    }

    protected void onViewCreated(View rootView) {
    }

    abstract protected @LayoutRes int getLayoutId();

    public void addState(int state, View view) {
        mStateViews.put(state, view);
        view.setVisibility(View.GONE);
        ((FrameLayout) mRootView).addView(view);
    }

    public void showEmptyView() {
        showStateView(STATE_EMPTY);
    }

    public void showErrorView() {
        showStateView(STATE_ERROR);
    }

    public void showNormalView() {
        showStateView(STATE_NORMAL);
    }

    protected void showStateView(int state) {
        for (int i = 0; i < mStateViews.size(); i++) {
            int key = mStateViews.keyAt(i);
            if (key == state) {
                mStateViews.valueAt(i).setVisibility(View.VISIBLE);
            } else {
                mStateViews.valueAt(i).setVisibility(View.GONE);
            }
        }
    }
    @Override
    public boolean isAlive() {
        return isAdded() && isVisible() && !isDetached() && getActivity() != null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        attach(getPresenter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detach(getPresenter());
    }

    @Override
    public void attach(P p) {
        p.start();
    }

    @Override
    public void detach(P p) {
        p.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissLoading();
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        if (isAlive()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
