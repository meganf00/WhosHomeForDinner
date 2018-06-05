package com.mad.whoshomefordinner.main.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.main.model.firebase.FirebaseInteractorImpl;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 20/5/18.
 *
 * MainPresenterImplis a class, which implements the  MainPresenter
 * and is used as a communication means between the MainVIew and
 * the MainFirebaseInteractor.
 */

public class MainPresenterImpl implements MainPresenter {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private MainView mMainView;
    private Activity mContext;
    private FirebaseInteractorImpl mFirebaseInteractor;
    private DatabaseReference mWHFDRef;


    /**
     * Constructor to create an instance of the MainPresenterImpl
     * for communication between the view and the model.
     * @param auth
     * @param context
     * @param WHFDRef
     */
    public MainPresenterImpl(FirebaseAuth auth, Activity context, DatabaseReference WHFDRef) {
        mAuth = auth;
        mContext = context;
        mWHFDRef = WHFDRef;

    }

    @Override
    public void setUpInteractor(){
        mFirebaseInteractor = new FirebaseInteractorImpl(mAuth, mContext, mWHFDRef);
    }

    @Override
    public MainPresenterImpl getPresenter() {
        return this;
    }

    @Override
    public void connectWithInteractor(){
        mFirebaseInteractor.getPresenter(this);
    }


    @Override
    public User getCurrentUser() {
        return mFirebaseInteractor.getUser();
    }

    @Override
    public void checkIfSignedIn() {
        if (!mFirebaseInteractor.isSignedIn()) {
            mMainView.goToLoginScreen();
        } else {
            mMainView.showProgressDialog();
            mFirebaseInteractor.createUser();

        }

    }

    @Override
    public void userCreated() {
        mMainView.setUpFragment();
    }


    @Override
    public void signOut() {
        mFirebaseInteractor.logOut();
        mMainView.goToLoginScreen();
    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void attachView(MainView view) {
        mMainView = view;
    }

    @Override
    public void detachView() {
        mMainView = null;
    }
}
