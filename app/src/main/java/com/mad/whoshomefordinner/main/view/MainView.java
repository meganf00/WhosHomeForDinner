package com.mad.whoshomefordinner.main.view;

import com.google.firebase.auth.FirebaseUser;
import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 20/5/18.
 */

public interface MainView extends BaseView{
    void setEnabled(boolean isEnabled);
    void setUser(FirebaseUser user);
    void isLogin(boolean isLogin);
}
