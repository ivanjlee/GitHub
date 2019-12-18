package com.ivan.github.core.mvp;

import android.widget.Toast;

import com.ivan.github.app.BaseFragment;

/**
 * com.ivan.github.core.mvp
 *
 * @author  Ivan J. Lee on 2019-11-19
 * @version v0.1
 * @since   v1.0
 **/
public abstract class IBaseMvpFragment<P extends IPresenter> extends BaseFragment implements IBaseStateView<P> {

    @Override
    public boolean isAlive() {
        return isAdded() && isVisible() && !isDetached() && getActivity() != null;
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
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showToast(String msg) {
        if (isAlive()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
