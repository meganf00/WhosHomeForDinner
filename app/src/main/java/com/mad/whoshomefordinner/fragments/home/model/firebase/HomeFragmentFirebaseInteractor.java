package com.mad.whoshomefordinner.fragments.home.model.firebase;

import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Megan on 29/5/18.
 *
 * HomeFragmentFirebaseInteractor is an interface which defines all the methods
 * required for for the presenter implementation classes that implement it
 */

public interface HomeFragmentFirebaseInteractor {

    /**
     * Gets presenter for the Group Fragment
     * @param mainPresenter
     */
    void getPresenter(HomeFragmentPresenter mainPresenter);

    /**
     * Returns the user created by Firebase Interactor
     * @return User
     */
    User getUser();

    /**
     * Returns the list of groups the user is a member of
     * from Firebase Interactor
     * @return List<Group>
     */
    List<Group> getGroups();

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
     * Returns a list of string of the allocated cook per group
     * @return
     */
    List<String> getAllocatedCooks();

    /**
     * Generates the cook names for each group with firebase
     */
    void generateCookNames();

    /**
     * Updates home status when called for the group based on the
     * position index passed
     * @param position
     */
    void updateHomeStatus(int position);

    /**
     * Checks if the current time is before the deadline
     * @param position
     * @return boolean
     * @throws ParseException
     */
    boolean nowIsBeforeDeadline(int position) throws ParseException;

    /**
     * Checks if the current user is the allocated cook for that day
     * @param position
     * @return
     */
    boolean userIsAllocatedCook(int position);

    /**
     * Creates a list of group objects from firebase data
     */
    void createGroups();
}
