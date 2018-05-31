package com.mad.whoshomefordinner.groupView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenrerImpl;
import com.mad.whoshomefordinner.groupView.view.GroupViewView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

public class GroupViewActivity extends AppCompatActivity implements GroupViewView {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private GroupViewPresenrerImpl mGroupViewPresenrer;

    private String mUserID;
    private String mUserName;
    private String mGroupID;

    private User mUser;

    TextView mWelcomeText;

    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent inComingData = getIntent();
        String action = inComingData.getAction();
        String type = inComingData.getType();



        mAuth = FirebaseAuth.getInstance();

        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child("User's").child(mUserID);

        mGroupViewPresenrer = new GroupViewPresenrerImpl(mAuth, mWHFDRef, mGroupID);

        mGroupViewPresenrer.attachView(this);
        mGroupViewPresenrer.setUpInteractor();
        mGroupViewPresenrer.connectWithInteractor();

        mGroupViewPresenrer.setUpUser();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setUpUser() {
        mUser = mGroupViewPresenrer.getCurrentUser();
    }

    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
        //TODO Set everything else as invisible
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void initiateView() {

    }

    public void setUpGroups() {
        mGroupViewPresenrer.setUpGroup();
    }

    @Override
    public Group getGroup() {
        return mGroupViewPresenrer.getGroup();
    }

    public void groupsCreated() {
        mGroupViewPresenrer.getGroup();
    }
}
