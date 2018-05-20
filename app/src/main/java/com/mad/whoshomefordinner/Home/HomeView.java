package com.mad.whoshomefordinner.Home;

import com.google.firebase.auth.FirebaseUser;
import com.mad.whoshomefordinner.Base.BaseView;

/**
 * Created by Megan on 20/5/18.
 */

public interface HomeView extends BaseView{
    void setEnabled(boolean isEnabled);
    void setUser(FirebaseUser user);
    void isLogin(boolean isLogin);
}
