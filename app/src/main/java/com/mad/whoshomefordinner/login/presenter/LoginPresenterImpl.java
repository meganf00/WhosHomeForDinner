package com.mad.whoshomefordinner.login.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mad.whoshomefordinner.login.view.LoginView;

/**
 * Created by Megan on 20/5/18.
 *
 * LoginPresenterImpl implements the interface LoginPresenter to communicate with
 * the View to check if the user's credentials are correct when logging in.
 *
 */

public class LoginPresenterImpl implements LoginPresenter {

    private FirebaseAuth mAuth;
    private LoginView mLoginView;

    /**
     * Constructor to initialise the method with the view and FirebaseAuth
     * @param auth
     */
    public LoginPresenterImpl(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    public void attachView(LoginView view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        mLoginView = null;
    }

    @Override
    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mLoginView, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mLoginView.loginSuccess();

                } else {
                    mLoginView.loginError();

                }
            }
        });

    }

    @Override
    public void checkLogin() {
        if (mAuth.getCurrentUser() != null) {
            mLoginView.isLogin(true);
        } else {
            mLoginView.isLogin(false);
        }

    }
}
