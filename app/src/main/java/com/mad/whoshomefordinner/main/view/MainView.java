package com.mad.whoshomefordinner.main.view;

import com.google.firebase.auth.FirebaseUser;
import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 20/5/18.
 */

public interface MainView extends BaseView{
    void goToLoginScreen();
    void setUpUser();
    void showProgressDialog();
    void hideProgressDialog();
    void setUpFragment();
}
