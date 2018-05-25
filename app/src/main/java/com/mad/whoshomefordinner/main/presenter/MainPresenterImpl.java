package com.mad.whoshomefordinner.main.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.mad.whoshomefordinner.main.view.MainView;

/**
 * Created by Megan on 20/5/18.
 */

public class MainPresenterImpl implements MainPresenter {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private MainView mMainView;
    private Activity mContext;

    public MainPresenterImpl(FirebaseAuth auth, final Activity context) {
        this.mAuth = auth;
        this.mContext = context;
    }


    @Override
    public void attachView(MainView view) {
        mMainView = view;
    }

    @Override
    public void detachView() {
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void getCurrentUser() {
        if (mAuth.getCurrentUser() != null) {
            mMainView.setUser(mAuth.getCurrentUser());
        }
    }

    @Override
    public void isSignedIn() {
        if (mAuth.getCurrentUser() != null) {
            mMainView.isLogin(true);
        } else {
            mMainView.isLogin(false);
        }
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

}
