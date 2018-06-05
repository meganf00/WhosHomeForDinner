package com.mad.whoshomefordinner.login.view;

import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 20/5/18.
 *
 * LoginView is an interface which defines all the methods
 * required for the activity classes that implement it
 */

public interface LoginView extends BaseView {
    /**
     * Is called when the user's has the correct credentials
     */
    void loginSuccess();

    /**
     * Called when the user has the incorrect credential
     */
    void loginError();

    /**
     * Checks if the user is logged in
     * @param isLogin
     */
    void isLogin(boolean isLogin);
}
