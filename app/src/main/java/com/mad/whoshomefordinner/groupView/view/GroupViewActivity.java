package com.mad.whoshomefordinner.groupView.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;
import com.mad.whoshomefordinner.viewgroupmembers.ViewGroupMembersActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupViewActivity extends AppCompatActivity implements GroupViewView {

    private static final String EXCEPTION_TAG = "Error";
    private static final String EXAMPLE_URL = "play.google.com";
    private static final String TAG = "Who's home for dinner";
    private static final String ON_ACT_RESULT = "onActivityResult: requestCode=";
    private static final String RESULT_CODE =  ", resultCode=";
    private static final String ON_ACT_RESULT_INV = "onActivityResult: sent invitation ";

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private GroupViewPresenterImpl mGroupViewPresenrer;

    private String mUserID;
    private String mUserName;
    private String mGroupID;
    private String nextCookingDay;

    private User mUser;

    private Group mGroup;

    @BindView(R.id.group_view_progress)
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

    @BindView((R.id.group_all))
    RelativeLayout mAllTxt;

    private String mAllocatedCookName;
    private String mHomeStatus;
    private int mMemberCount;
    private static final String INTENT_GROUP_ID = "groupID";
    private static final String DB_USER_REF = "User's";
    private static String USER_ID = "userID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ButterKnife.bind(this);


        setTitle(getString(R.string.groups_txt));





        Intent inComingData = getIntent();
        String groupID = inComingData.getStringExtra(INTENT_GROUP_ID);

        mGroupID = groupID;



        mAuth = FirebaseAuth.getInstance();

        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child(DB_USER_REF).child(mUserID);

        mGroupViewPresenrer = new GroupViewPresenterImpl(mAuth, mWHFDRef, mGroupID);

        mGroupViewPresenrer.attachView(this);
        mGroupViewPresenrer.setUpInteractor();
        mGroupViewPresenrer.connectWithInteractor();

        showProgressDialog();

        mGroupViewPresenrer.setUpUser();



    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void setUpUser() {
        mUser = mGroupViewPresenrer.getCurrentUser();

        setUpGroups();
    }

    @Override
    public void showProgressDialog() {
        mAllTxt.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mAllTxt.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void initiateView() {
        CollapsingToolbarLayout cToolbar = findViewById(R.id.toolbar_layout);
        cToolbar.setTitle(mGroup.getName());

        if (userIsAllocatedCook()) {
            mCookName.setText(R.string.you_are_txt);
            mResponseTxt.setVisibility(View.GONE);
            mHomeResponse.setVisibility(View.GONE);
        } else {
            mCookName.setText(mAllocatedCookName +  getString(R.string.is_txt));
            mHomeResponse.setText(mHomeStatus);
        }

        mCookingMealTxt.setText(getString(R.string.is_cooking_txt) + mGroup.getMeal());
        mGroupNo.setText(Integer.toString(mMemberCount));
        mNextCook.setText(nextCookingDay);


        hideProgressDialog();
    }

    private boolean userIsAllocatedCook() {
        return mGroupViewPresenrer.isUserAllocatedCook();
    }

    @Override
    public void setUpGroups() {
        mGroupViewPresenrer.setUpGroup();
    }

    @Override
    public void getAllocatedCookName() {
        mAllocatedCookName = mGroupViewPresenrer.getAllocatedCook();

        if (userIsAllocatedCook()) {

        } else {
            if (mGroupViewPresenrer.userIsHome()) {
                mHomeStatus = getString(R.string.home);
            } else {
                mHomeStatus = getString(R.string.not_home);
            }
        }

        setNextCookingDay();


    }

    private void setNextCookingDay() {
        mGroupViewPresenrer.setNextDate();
    }

    @Override
    public void getNextCookingDay() {
        try {
            nextCookingDay = mGroupViewPresenrer.getNextCookingDay();
        } catch (NullPointerException e) {
            Log.e(EXCEPTION_TAG, getClass() +  e.getMessage());
            nextCookingDay = getString(R.string.try_again);
        }

        if (nextCookingDay.equals("")) {
            nextCookingDay = getString(R.string.wed);
        }

        getMemberCount();
        initiateView();
    }

    @Override
    public void getMemberCount() {
        mMemberCount = mGroupViewPresenrer.getNoMembers();
    }

    @Override
    public void getGroup() {
        mGroup = mGroupViewPresenrer.getGroup();
        mGroupViewPresenrer.setUpAllocatedCook();

    }

    public void seeGroupMembers(View view) {
        Intent intent = new Intent(this, ViewGroupMembersActivity.class);
        intent.putExtra(USER_ID , mUserID);
        startActivity(intent);
    }

    public void onInviteClicked(View view) {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invite_freinds))
                .setMessage(getString(R.string.come_try))
                .setDeepLink(Uri.parse(EXAMPLE_URL))
                .build();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, ON_ACT_RESULT + requestCode + RESULT_CODE + resultCode);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, ON_ACT_RESULT_INV + id);
                }
            } else {
                Toast.makeText(getContext(), R.string.message_fail_toast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
