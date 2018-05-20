package com.mad.whoshomefordinner.Home;

import com.mad.whoshomefordinner.Base.BasePresenter;

/**
 * Created by Megan on 20/5/18.
 */

public interface HomePresenter extends BasePresenter<HomeView> {
    void getCurrentUser();
    void isSignedIn();
    void signOut();
    void onStart();
    void onStop();
}
