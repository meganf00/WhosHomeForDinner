package com.mad.whoshomefordinner.login.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.login.view.LoginView;

/**
 * Created by Megan on 20/5/18.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void login(String email, String password);
    void checkLogin();
}
