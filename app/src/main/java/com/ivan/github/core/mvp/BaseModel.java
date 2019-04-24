package com.ivan.github.core.mvp;

/**
 * MVP - M
 *
 * @author  Ivan J. Lee on 2019-04-22 00:21.
 * @version v0.1
 * @since   v1.0
 */
public abstract class BaseModel<Presenter> implements IModel {

    private Presenter mPresenter;

    public BaseModel(Presenter presenter) {
        this.mPresenter = presenter;
    }

    void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }
}
