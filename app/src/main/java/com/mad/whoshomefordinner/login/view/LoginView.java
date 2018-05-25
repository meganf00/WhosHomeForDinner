package com.mad.whoshomefordinner.login.view;

import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 20/5/18.
 */

public interface LoginView extends BaseView {
    void showValidationError(String message);
    void loginSuccess();
    void loginError();
    void isLogin(boolean isLogin);
}
