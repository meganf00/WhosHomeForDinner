package com.mad.whoshomefordinner.fragments.home.model;

import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 29/5/18.
 */

public interface HomeFragmentFirebaseInteractor {
    void getPresenter(HomeFragmentPresenter mainPresenter);
    User getUser();
    List<Group> getGroups();
    void createUser();
    boolean userCreated();

    List<String> getAllocatedCooks();
}
