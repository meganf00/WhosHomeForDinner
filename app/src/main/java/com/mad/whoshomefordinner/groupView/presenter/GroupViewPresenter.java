package com.mad.whoshomefordinner.groupView.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.groupView.view.GroupViewView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 31/5/18.
 *
 * GroupViewPresenter is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface GroupViewPresenter extends BasePresenter<GroupViewView> {

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
     * @return GroupViewPresenterImpl
     */
    GroupViewPresenterImpl getPresenter();

    /**
     * Method called by View to connect with Firebase Interactor
     */
    void connectWithInteractor();

    /**
     * Calls Firebase Interactor to create user object
     */
    void setUpUser();

    /**
     * Calls firebase interactor to set up the Allocated cook
     */
    void setUpAllocatedCook();

    /**
     * Checks if the current user is the allocated cook and
     * returns the result
     * @return boolean
     */
    boolean isUserAllocatedCook();

    /**
     * Called by firebase interactor when the allocated cook names have been generated
     */
    void allocatedCookNameGenerated();

    /**
     * Returns the allocated cook name
     * @return String
     */
    String getAllocatedCook();

    /**
     * Check's the users home status ans returns the result
     * @return boolean
     */
    boolean userIsHome();

    /**
     * Generates the next date the user is allocated to cook
     */
    void setNextDate();

    /**
     * Called when the days and the cooks are generated
     */
    void cookingDaysGenerated();

    /**
     * Gets the next day the user is cooking and returns it
     * @return String
     */
    String getNextCookingDay();

    /**
     * Get's the number of members and returns it
     * @return int
     */
    int getNoMembers();
}
