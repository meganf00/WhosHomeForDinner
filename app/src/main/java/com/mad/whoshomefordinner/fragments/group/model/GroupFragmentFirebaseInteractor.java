package com.mad.whoshomefordinner.fragments.group.model;

import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenter;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 30/5/18.
 */

public interface GroupFragmentFirebaseInteractor {
    void getPresenter(GroupFragmentPresenter mainPresenter);
    User getUser();
    List<Group> getGroups();
    void createUser();
    boolean userCreated();
}
