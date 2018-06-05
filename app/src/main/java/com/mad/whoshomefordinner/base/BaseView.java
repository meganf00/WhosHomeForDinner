package com.mad.whoshomefordinner.base;

import android.content.Context;

/**
 * Created by Megan on 20/5/18.
 *
 * BaseView is the base java class implemented by
 * each View interface in this application, used to share common methods
 * between classes implementing the View interface
 */

public interface BaseView {

    /**
     * Method which gets the activities context;
     *
     * @return Context
     */
    Context getContext();
}
