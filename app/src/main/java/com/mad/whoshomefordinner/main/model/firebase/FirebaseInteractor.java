package com.mad.whoshomefordinner.main.model.firebase;

import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 27/5/18.
 *
 * FirebaseInteractor is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface FirebaseInteractor {

    /**
     * Gets presenter for the Group Fragment
     * @param mainPresenter
     */
    void getPresenter(MainPresenterImpl mainPresenter);

    /**
     * Returns the user created by Firebase Interactor
     * @return User
     */
    User getUser();

    /**
     * Returns if the user is sign in or not
     * @return boolean
     */
    boolean isSignedIn();

    /**
     * Logs user out
     */
    void logOut();

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
