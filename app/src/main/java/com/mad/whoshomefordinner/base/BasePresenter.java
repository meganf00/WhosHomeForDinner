package com.mad.whoshomefordinner.base;

/**
 * Created by Megan on 20/5/18.
 *
 * BasePresenter is the base java class implemented by
 * each presenter in this application, used to share common methods
 * between presenter classes
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * Method which connects the view with the presenter
     * @param view
     */
    void attachView(V view);

    /**
     * Method which disconnects the view with the presenter
     */
    void detachView();
}
