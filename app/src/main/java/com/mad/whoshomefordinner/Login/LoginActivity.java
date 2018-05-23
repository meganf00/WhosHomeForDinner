package com.mad.whoshomefordinner.Login;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mad.whoshomefordinner.Base.BaseActivity;
import com.mad.whoshomefordinner.Home.HomeActivity;
import com.mad.whoshomefordinner.R;

import butterknife.OnClick;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements LoginView {

    private FirebaseAuth mAuth;
    private LoginPresenter mLoginPresenter;

    @BindView(R.id.email)
     AutoCompleteTextView mEmailView;

    @BindView(R.id.password)
     EditText mPasswordView;


    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mLoginPresenter = new LoginPresenterImpl(mAuth);

        mLoginPresenter.attachView(this);
        mLoginPresenter.checkLogin();

    }

    @OnClick(R.id.email_sign_in_btn) void loginBtnClick() {
        String emailTxt = mEmailView.getText().toString().trim();
        String passwordTxt = mPasswordView.getText().toString().trim();
        Log.d("TAG", "Sign in button clicked");
        mLoginPresenter.login(emailTxt, passwordTxt);
    }




    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showValidationError(String message) {

    }

    @Override
    public void loginSuccess() {
        Log.d("TAG", "signInWithEmail:success");
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError() {
        Log.w("TAG", "signInWithEmail:failure");
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isLogin(boolean isLogin) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

    }

    }


