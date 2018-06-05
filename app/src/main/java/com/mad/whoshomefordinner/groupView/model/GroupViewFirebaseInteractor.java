package com.mad.whoshomefordinner.groupView.model;

import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 31/5/18.
 *
 * GroupViewFirebaseInteractor is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface GroupViewFirebaseInteractor {

    /**
     * Gets presenter for the Group Fragment
     * @param mainPresenter
     */
    void getPresenter(GroupViewPresenter mainPresenter);

    /**
     * Returns the user created by Firebase Interactor
     * @return User
     */
    User getUser();

    /**
     *  Returns the the group selected
     * from Firebase Interactor
     * @return Group
     */
    Group getGroup();

    /**
     * Calls Firebase Interactor to create user object
     */
    void createUser();

    /**
     * Called when user is created by Firebase Interactor
     * @return boolean
     */
    boolean userCreated();

    /**
     * Generates the allocated cook for that day from firebase lofic
     */
    void generateAllocatedCook();

    /**
     * Checks if the user the user is the cook today
     * and returns the result
     * @return boolean
     */
    boolean checkUserIsCook();

    /**
     * Gets the name of the allocated cook and returns it
     * @return String
     */
    String getAllocatedCookName();

    /**
     * Checks the user's home state and returns the result
     * @return String
     */
    Boolean isUserHome();

    /**
     * Finds the next day the user is cooking
     */
    void findNextCookDay();

    /**
     * Gets the next day the user is cooking and returns it
     * @return String
     */
    String getNextCookDay();

    /**
     * Gets the member count of the group and returns it
     * @return
     */
    int getMemberCount();

    /**
     * Creates the group object based on the group selected
     */
    void createGroup();
}
