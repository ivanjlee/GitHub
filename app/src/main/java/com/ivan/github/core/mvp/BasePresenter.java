package com.ivan.github.core.mvp;

/**
 * BasePresenter
 *
 * @author  Ivan J. Lee on 2019-04-22 00:23.
 * @version v0.1
 * @since   v1.0
 */
public abstract class BasePresenter<View extends BaseView, Model extends BaseModel<? extends BasePresenter>>
        implements IPresenter {

    private View mView;
    private Model mModel;

    public BasePresenter(View mView) {
        this.mView = mView;
        this.mModel = createModel(this);
    }

    protected abstract Model createModel(BasePresenter presenter);

    protected Model getModel() {
        return mModel;
    }

    protected View getView() {
        return mView;
    }
}
