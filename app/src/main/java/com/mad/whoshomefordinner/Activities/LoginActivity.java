package com.mad.whoshomefordinner.Activities;

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
import com.mad.whoshomefordinner.Login.LoginPresenter;
import com.mad.whoshomefordinner.Login.LoginPresenterImpl;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.Login.LoginView;

import butterknife.OnClick;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements LoginView {

    private FirebaseAuth mAuth;
    private LoginPresenter mLoginPresenter;

    // UI references.
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

//        Button mEmailSignInButton = findViewById(R.id.email_sign_in_btn);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                        String emailTxt = mEmailView.getText().toString().trim();
//        String passwordTxt = mPasswordView.getText().toString().trim();
//        Log.d("TAG", "Sign in button clicked");
//        mLoginPresenter.login(emailTxt, passwordTxt);
//            }
//        });


//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_btn);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = mEmailView.getText().toString();
//                final String password = mPasswordView.getText().toString();


//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("TAG", "signInWithEmail:success");
//                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//
//
//                            }
//                        });
//
//
//            }
//        });

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
    public void showValideationError(String message) {

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


