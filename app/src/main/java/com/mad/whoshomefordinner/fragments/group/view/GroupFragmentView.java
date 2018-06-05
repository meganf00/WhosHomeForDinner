package com.mad.whoshomefordinner.fragments.group.view;

import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 30/5/18.
 *
 * GroupFragmentView is an interface which defines all the methods
 * required for the activity classes that implement it
 */

public interface GroupFragmentView extends BaseView {

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
}
