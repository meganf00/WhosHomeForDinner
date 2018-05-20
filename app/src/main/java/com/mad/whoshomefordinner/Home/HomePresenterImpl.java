package com.mad.whoshomefordinner.Home;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Megan on 20/5/18.
 */

public class HomePresenterImpl implements HomePresenter{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private HomeView mHomeView;
    private Activity mContext;

    public HomePresenterImpl(FirebaseAuth auth, final Activity context) {
        this.mAuth = auth;
        this.mContext = context;
    }


    @Override
    public void attachView(HomeView view) {
        mHomeView = view;
    }

    @Override
    public void detachView() {
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void getCurrentUser() {
        if (mAuth.getCurrentUser() != null) {
            mHomeView.setUser(mAuth.getCurrentUser());
        }
    }

    @Override
    public void isSignedIn() {
        if (mAuth.getCurrentUser() != null) {
            mHomeView.isLogin(true);
        } else {
            mHomeView.isLogin(false);
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
