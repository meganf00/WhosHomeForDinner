package com.mad.whoshomefordinner.fragments.home.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.fragments.home.model.HomeFragmentFirebaseInteractorImpl;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragment;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragmentView;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 29/5/18.
 */

public class HomeFragmentPresenterImpl implements HomeFragmentPresenter {

    FirebaseAuth mAuth;
    private HomeFragmentView mHomeFragmentView;
    private Activity mContext;
    private HomeFragmentFirebaseInteractorImpl mHomeFragFBInteratorImpl;
    private DatabaseReference mWHFDRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

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
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void userCreated() {
        mHomeFragmentView.setUpFragment();
    }
}
