package com.mad.whoshomefordinner.main.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 20/5/18.
 *
 * MainPresenter is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface MainPresenter extends BasePresenter<MainView> {

    /**
     * Returns the current user logged in
     * @return User
     */
    User getCurrentUser();

    /**
     * Checks if there is a current user session
     */
    void checkIfSignedIn();

    /**
     * Tells firebase interactor to sign out user
     */
    void signOut();

    /**
     * Attaches view when activity starts
     */
    void onStart();

    /**
     * Detaches view when activity stops
     */
    void onStop();

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
     *
     * @return MainPresenterImpl
     */
    MainPresenterImpl getPresenter();

    /**
     * Method called by View to connect with Firebase Interactor
     */
    void connectWithInteractor();
}
