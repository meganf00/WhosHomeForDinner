package com.mad.whoshomefordinner.main.view;

import com.google.firebase.auth.FirebaseUser;
import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 20/5/18.
 *
 * MainView is an interface which defines all the methods
 * required for the activity classes that implement it
 */

public interface MainView extends BaseView{
    /**
     * Open LoginActivity if user is not logged in
     */
    void goToLoginScreen();

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
     * Calls and creates selected fragment
     */
    void setUpFragment();
}
