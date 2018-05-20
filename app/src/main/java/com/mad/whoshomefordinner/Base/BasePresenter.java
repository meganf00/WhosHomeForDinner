package com.mad.whoshomefordinner.Base;

/**
 * Created by Megan on 20/5/18.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
