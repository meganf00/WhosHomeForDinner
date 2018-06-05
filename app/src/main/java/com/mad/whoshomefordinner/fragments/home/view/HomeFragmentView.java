package com.mad.whoshomefordinner.fragments.home.view;

import android.view.View;

import java.util.List;

/**
 * Created by Megan on 29/5/18.
 *
 * GroupFragmentView is an interface which defines all the methods
 * required for the activity classes that implement it
 */

public interface HomeFragmentView {

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
     * Method which calls particular methods to set up the data required by the gragment
     */
    void setUpFragment();

    /**
     * Calls presenter to set up the list of group objects
     */
    void setUpGroups();

    /**
     * Gets the list of groups created
     */
    void getGroups();

    /**
     * Method which updates the recycler view row onClick
     */
    void updateRow();

    /**
     * Initialises and creates the view. It is called after acquiring all required data
     */
    void initiateView();

    /**
     * Get's allocated cooks for the week
     */
    void getAllocatedCooks();

    /**
     * Tells presenter to update home status when clicked
     * @param view
     * @param position
     */
    void updateHomeStatus(View view, int position);


    /**
     * Show's toast when user clicks recycler view to edit their
     * home response after the deadline time
     */
    void showPastDeadlineToast();

    /**
     * Show's toast when user clicks recycler view to notify user they
     * are the allocated cook
     */
    void createAllocatedCookToast();
}
