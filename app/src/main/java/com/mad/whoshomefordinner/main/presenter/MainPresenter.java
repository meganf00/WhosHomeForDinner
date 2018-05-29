package com.mad.whoshomefordinner.main.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 20/5/18.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    User getCurrentUser();
    void checkIfSignedIn();
    void signOut();
    void onStart();
    void onStop();
    void userCreatedYay();
    void userCreated();
}
