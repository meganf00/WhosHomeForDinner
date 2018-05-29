package com.mad.whoshomefordinner.fragments.home.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragment;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 29/5/18.
 */

public interface HomeFragmentPresenter extends BasePresenter<HomeFragment> {
    User getCurrentUser();
    void onStart();
    void onStop();
    void userCreated();
    void setUpInteractor();
    HomeFragmentPresenterImpl getPresenter();
    void connectWithInteractor();
    void setUpUser();
}
