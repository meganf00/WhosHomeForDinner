package com.mad.whoshomefordinner.fragments.home.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragment;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 29/5/18.
 *
 * HomeFragmentPresenter is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface HomeFragmentPresenter extends BasePresenter<HomeFragment> {

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
     * @return HomeFragmentPresenterImpl
     */
    HomeFragmentPresenterImpl getPresenter();

    /**
     * Method called by View to connect with Firebase Interactor
     */
    void connectWithInteractor();

    /**
     * Calls Firebase Interactor to create user object
     */
    void setUpUser();

    /**
     * Returns the list of Strings of the allocated cooks names
     * created in Firebase Interactor
     * @return List<String>
     */
    List<String> getAllocatedCooksNames();

    /**
     * Called by Firebase interactor to notify presenter the allocated cook name
     * has been found
     */
    void allocatedCooksGenerated();

    /**
     * Tells firebase interactor to set up allocated cook name
     */
    void setUpAllocatedCooks();

    /**
     * Called when the onclick function has completed and the view needs
     * to update the changes
     */
    void rowClickFinished();

    /**
     * Tells presenter it is past the deadline to change home response
     */
    void pastDeadline();

    /**
     * Tells interactor to check if user is the allocated cook for today
     */
    void userIsAllocatedCook();

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

    /**
     * Handles onClick of the row click to update the user's status
     * @param position
     */
    void handleRowClick(int position);
}
