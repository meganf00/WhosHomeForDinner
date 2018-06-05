package com.mad.whoshomefordinner.groupView.view;

import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.model.Group;

/**
 * Created by Megan on 31/5/18.
 *
 * GroupViewView is an interface which defines all the methods
 * required for the activity classes that implement it
 */

public interface GroupViewView extends BaseView {

    /**
     *Calls presenter to set up the user object
     */
    void setUpUser();

    /**
     * Set's the visibility of the progress dialog to visible
     */
    void showProgressDialog();

    /**
     * Set's the visibilty of the progress dialog to invisible
     */
    void hideProgressDialog();

    /**
     * Gets the list of groups created
     */
    void getGroup();

    /**
     * Initialises and creates the view. It is called after acquiring all required data
     */
    void initiateView();

    /**
     * Calls presenter to set up the list of group objects
     */
    void setUpGroups();

    /**
     * Get's the name of the allocated cook
     */
    void getAllocatedCookName();

    /**
     * Get's the next known day the user will be allocated to cook
     */
    void getNextCookingDay();

    /**
     * Get's the count of how many members are in the group
     */
    void getMemberCount();

    /**
     * Check's if the user is allocated to cook
     * @return boolean
     */
    boolean userIsAllocatedCook();

    /**
     * Set's up the next cooking day by calling the presenter
     */
    void setNextCookingDay();
}
