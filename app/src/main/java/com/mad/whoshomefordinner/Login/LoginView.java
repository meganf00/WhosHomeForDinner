package com.mad.whoshomefordinner.Login;

import com.mad.whoshomefordinner.Base.BaseView;

/**
 * Created by Megan on 20/5/18.
 */

public interface LoginView extends BaseView {
    void showValideationError(String message);
    void loginSuccess();
    void loginError();
    void isLogin(boolean isLogin);
}
