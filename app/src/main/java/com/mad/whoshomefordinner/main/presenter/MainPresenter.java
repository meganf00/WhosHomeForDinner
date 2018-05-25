package com.mad.whoshomefordinner.main.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.main.view.MainView;

/**
 * Created by Megan on 20/5/18.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void getCurrentUser();
    void isSignedIn();
    void signOut();
    void onStart();
    void onStop();
}
