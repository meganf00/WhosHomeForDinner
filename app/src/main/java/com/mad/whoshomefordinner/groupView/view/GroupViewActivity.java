package com.mad.whoshomefordinner.groupView.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.mad.whoshomefordinner.viewgroupmembers.ViewGroupMembersActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupViewActivity extends AppCompatActivity implements GroupViewView {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private GroupViewPresenrerImpl mGroupViewPresenrer;

    private String mUserID;
    private String mUserName;
    private String mGroupID;

    private User mUser;

    private Group mGroup;

    TextView mWelcomeText;

    ProgressBar mProgressBar;

    Toolbar mToolbar;

    @BindView(R.id.cook_name)
    TextView mCookName;

    @BindView(R.id.is_cooking_txt)
    TextView mCookingMealTxt;

    @BindView(R.id.response)
    TextView mResponseTxt;

    @BindView(R.id.home_response)
    TextView mHomeResponse;

    @BindView(R.id.next_cooking_result)
    TextView mNextCook;

    @BindView(R.id.details)
    LinearLayout mGroupMembersDetails;

    @BindView(R.id.invite)
    LinearLayout mInivteButton;

    @BindView(R.id.group_no_txt)
    TextView mGroupNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ButterKnife.bind(this);

        setTitle(getString(R.string.groups_txt));





        Intent inComingData = getIntent();
        String groupID = inComingData.getStringExtra("groupID");

        mGroupID = groupID;



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

        setUpGroups();
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
        CollapsingToolbarLayout cToolbar = findViewById(R.id.toolbar_layout);
        cToolbar.setTitle(mGroup.getName());

        mCookingMealTxt.setText(" is cooking" + mGroup.getMeal());
        mGroupNo.setText(String.valueOf(mGroup.getGroupMembers().size()));


    }

    @Override
    public void setUpGroups() {
        mGroupViewPresenrer.setUpGroup();
    }

    @Override
    public void getGroup() {
        mGroup = mGroupViewPresenrer.getGroup();
        initiateView();
    }

    public void groupsCreated() {
        mGroupViewPresenrer.getGroup();
    }

    public void seeGroupMembers(View view) {
        Intent intent = new Intent(this, ViewGroupMembersActivity.class);
        intent.putExtra("USERID", mUserID);
        startActivity(intent);
    }
}
