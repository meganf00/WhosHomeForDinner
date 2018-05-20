package com.mad.whoshomefordinner.Login;

import com.mad.whoshomefordinner.Base.BasePresenter;

/**
 * Created by Megan on 20/5/18.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void login(String email, String password);
    void checkLogin();
}
