package com.mad.whoshomefordinner.fragments.group.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.fragments.group.view.GroupFragment;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 30/5/18.
 */

public interface GroupFragmentPresenter extends BasePresenter<GroupFragment> {

    User getCurrentUser();
    void onStart();
    void onStop();
    void userCreated();
    void setUpInteractor();
    GroupFragmentPresenterImpl getPresenter();
    void connectWithInteractor();
    void setUpUser();
}
