package com.mad.whoshomefordinner.groupView.model;

import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 31/5/18.
 */

public interface GroupViewFirebaseInteractor {
    void getPresenter(GroupViewPresenter mainPresenter);
    User getUser();
    Group getGroup();
    void createUser();
    boolean userCreated();

}
