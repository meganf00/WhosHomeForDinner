package com.mad.whoshomefordinner.fragments.group.model;

import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenter;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 30/5/18.
 *
 * GroupFragmentFirebaseInteractor is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface GroupFragmentFirebaseInteractor {

    /**
     * Gets presenter for the Group Fragment
     * @param mainPresenter
     */
    void getPresenter(GroupFragmentPresenter mainPresenter);

    /**
     * Returns the user created by Firebase Interactor
     * @return User
     */
    User getUser();

    /**
     * Returns the list of groups the user is a member of
     * from Firebase Interactor
     * @return List<Group>
     */
    List<Group> getGroups();

    /**
     * Calls Firebase Interactor to create user object
     */
    void createUser();

    /**
     * Called when user is created by Firebase Interactor
     * @return boolean
     */
    boolean userCreated();
}
