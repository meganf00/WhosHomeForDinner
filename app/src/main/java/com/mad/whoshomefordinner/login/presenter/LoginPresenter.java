package com.mad.whoshomefordinner.login.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.login.view.LoginView;

/**
 * Created by Megan on 20/5/18.
 *
 * LoginPresenter is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface LoginPresenter extends BasePresenter<LoginView> {

    /**
     * Performs login operation to check if user has the correct credentials
     * @param email
     * @param password
     */
    void login(String email, String password);

    /**
     * Is called to check if the login operation was successful or not
     */
    void checkLogin();
}
