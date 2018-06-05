package com.mad.whoshomefordinner.fragments.group.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.fragments.group.view.GroupFragment;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 30/5/18.
 *
 * GroupFragmentPresenter is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface GroupFragmentPresenter extends BasePresenter<GroupFragment> {

    /**
     * Returns the current user logged in
     * @return User
     */
    User getCurrentUser();

    /**
    * Called by the Firebase Implementation when the user object has been created
     */
    void userCreated();

    /**
    * Initialises Firebase Interactor for the fragment
     */
    void setUpInteractor();

    /**
     * Method which returns the current presenter to the view
     * @return GroupFragmentPresenterImpl
     */
    GroupFragmentPresenterImpl getPresenter();

    /**
    * Method called by View to connect with Firebase Interactor
     */
    void connectWithInteractor();

    /**
    * Calls Firebase Interactor to create user object
     */
    void setUpUser();

    /**
    * Calls Firebase Interactor to create a list of groups
     */
    void setUpGroups();

    /**
     * Returns the list of groups created in Firebase Interactor
     * @return List<Group>
     */
    List<Group> getGroups();

    /**
    * Called by Firebase Interactor when the list of groups
    * has been created
     */
    void groupsCreated();
}
