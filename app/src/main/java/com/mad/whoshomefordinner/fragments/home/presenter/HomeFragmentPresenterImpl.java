package com.mad.whoshomefordinner.fragments.home.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.fragments.home.model.firebase.HomeFragmentFirebaseInteractorImpl;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragment;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragmentView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 29/5/18.
 *
 * HomeFragmentPresenterImpl is a class, which implements the HomeFragmentPresenter
 * and is used as a communication means between the HomeFragmentVIew and
 * the HomeFragmentFirebaseInteractor.
 */

public class HomeFragmentPresenterImpl implements HomeFragmentPresenter {

    private FirebaseAuth mAuth;
    private HomeFragmentView mHomeFragmentView;
    private HomeFragmentFirebaseInteractorImpl mHomeFragFBInteratorImpl;
    private DatabaseReference mWHFDRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    /**
     * Constructor to create an instance of the HomeFragmentPresenterImpl
     * for communication between the view and the model.
     * @param auth
     * @param WHFDRef
     */
    public HomeFragmentPresenterImpl(FirebaseAuth auth, DatabaseReference WHFDRef){
        mAuth = auth;
        mWHFDRef = WHFDRef;
    }

    @Override
    public void setUpInteractor() {
        mHomeFragFBInteratorImpl = new HomeFragmentFirebaseInteractorImpl(mAuth, mWHFDRef);
    }

    @Override
    public HomeFragmentPresenterImpl getPresenter() {
        return this;
    }

    @Override
    public void connectWithInteractor() {
        mHomeFragFBInteratorImpl.getPresenter(this);
    }

    @Override
    public void setUpUser() {
        mHomeFragFBInteratorImpl.createUser();
    }



    @Override
    public void allocatedCooksGenerated(){
        mHomeFragmentView.getAllocatedCooks();
    }

    @Override
    public void setUpAllocatedCooks() {
        mHomeFragFBInteratorImpl.generateCookNames();
    }

    @Override
    public List<String> getAllocatedCooksNames() {
        return mHomeFragFBInteratorImpl.getAllocatedCooks();
    }


    @Override
    public void attachView(HomeFragment view) {
        mHomeFragmentView = view;
    }

    @Override
    public void detachView() {
        mHomeFragmentView = null;
    }

    @Override
    public User getCurrentUser() {
        return mHomeFragFBInteratorImpl.getUser();
    }

    @Override
    public void setUpGroups() {
        mHomeFragFBInteratorImpl.createGroups();
    }

    @Override
    public List<Group> getGroups() {
        return mHomeFragFBInteratorImpl.getGroups();
    }

    @Override
    public void groupsCreated() {
        mHomeFragmentView.getGroups();
    }


    @Override
    public void userCreated() {
        mHomeFragmentView.setUpFragment();
    }

    @Override
    public void handleRowClick(int position) {
        mHomeFragFBInteratorImpl.updateHomeStatus(position);
    }

    @Override
    public void rowClickFinished(){
        mHomeFragmentView.updateRow();
    }

    @Override
    public void pastDeadline() {
        mHomeFragmentView.showPastDeadlineToast();
    }

    @Override
    public void userIsAllocatedCook() {
        mHomeFragmentView.createAllocatedCookToast();
    }


}
