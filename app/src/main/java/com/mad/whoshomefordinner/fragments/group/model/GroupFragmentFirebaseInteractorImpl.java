package com.mad.whoshomefordinner.fragments.group.model;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenter;
import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenterImpl;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Megan on 30/5/18.
 */

public class GroupFragmentFirebaseInteractorImpl implements GroupFragmentFirebaseInteractor{

    FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    private String mUserID;
    private Activity mContext;

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mGroupRef;

    private String mUserName;
    private String mUserEmail;
    private List<String> mGroupIDs = new ArrayList<>();
    private List<Group> mGroups = new ArrayList<>();
    private User mUser;

    private String mGroupID;
    private String mGroupName;
    private List<String> mGroupMembers = new ArrayList<>();
    private List<String> mGroupWeekDays;
    private String mGroupDay;
    private String mGroupAllocatedCook;
    private String mGroupMeal;
    private String mGroupDeadline;

    public Group group;

    private GroupFragmentPresenterImpl mGroupFragmentPresenter;

    private int count = 0;
    private int groupCount = 0;

    private static final String DB_USER_REF = "User's";
    private static final String DB_GROUP_REF = "Groups";
    private static final String NAME_DB = "Name";
    private static final String EMAIL_DB = "Email";
    private static final String GROUP_DB = "Groups";
    private static final String WEEK_DB = "Current Week";
    private static final String ALL_COOK_DB = "Allocated cook";
    private static final String DEADLINE_DB = "Deadline";
    private static final String MEAL_DB = "Meal";
    private static final String HOME_DB = "Home";
    private static final String TRUE_DB = "True";
    private static final String FALSE_DB = "False";
    private static final String TAG = "Who's Home For Dinner" ;
    private static final String NO_DATA = "Database error";

    public GroupFragmentFirebaseInteractorImpl(FirebaseAuth auth, DatabaseReference WHFDRef) {
        mAuth = auth;

        mFirebaseUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = WHFDRef;
        mUserRef = mWHFDRef.child(DB_USER_REF).child(mUserID);
        mGroupRef = mWHFDRef.child(DB_GROUP_REF);
    }

    @Override
    public void getPresenter(GroupFragmentPresenter mainPresenter) {
        mGroupFragmentPresenter = mainPresenter.getPresenter();
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public List<Group> getGroups() {
        return mGroups;
    }

    @Override
    public void createUser() {
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (NAME_DB.equals(item.getKey())) {
                            mUserName = item.getValue().toString();
                        } else if (EMAIL_DB.equals(item.getKey())) {
                            mUserEmail = item.getValue().toString();
                        } else if (GROUP_DB.equals(item.getKey())) {
                            Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                            for (DataSnapshot data : groupSnapShot) {
                                String temp = data.getKey().toString();
                                mGroupIDs.add(temp);
                            }
                        }
                    }
                }
                else {
                    Log.d(TAG, NO_DATA);
                }

                mUser = new User(mUserID, mUserName, mUserEmail, mGroupIDs);
                userCreated();
                mGroupFragmentPresenter.userCreated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
    }

    public void createGroups(){

        groupCount = 0;

        String weekDay = "";
        Calendar c = Calendar.getInstance();
        mGroupDay = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        mGroupDay = weekDay;


        for (final String groupID : mGroupIDs) {
            mGroupRef.child(groupID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.getKey().equals(NAME_DB)) {
                                mGroupName = item.getValue().toString();
                            } else if (item.getKey().equals(WEEK_DB)) {
                                Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                                for (DataSnapshot data : groupSnapShot) {
                                    if (data.getKey().equals(mGroupDay)) {
                                        Iterable<DataSnapshot> daySnapShot = data.getChildren();
                                        for (DataSnapshot data2 : daySnapShot) {
                                            if (data2.getKey().equals(ALL_COOK_DB)) {
                                                mGroupAllocatedCook = data2.getValue().toString();
                                            } else if (data2.getKey().equals(DEADLINE_DB)) {
                                                mGroupDeadline = data2.getValue().toString();
                                            } else if (data2.getKey().equals(MEAL_DB)) {
                                                mGroupMeal = data2.getValue().toString();
                                            } else if (data2.getKey().equals(HOME_DB)) {
                                                Iterable<DataSnapshot> homeSnapShot = data2.getChildren();
                                                for (DataSnapshot data3 : homeSnapShot) {
                                                    if (data3.getValue().equals(TRUE_DB)) {
                                                        String temp = data3.getKey().toString();
                                                        mGroupMembers.add(temp);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        group = new Group(groupID, mGroupName, mGroupMembers, mGroupDay, mGroupAllocatedCook, mGroupMeal, mGroupDeadline);
                    }

                    mGroups.add(group);

                    groupCount += 1;


                    if (mGroupIDs.size() == groupCount) {

                        mGroupFragmentPresenter.groupsCreated();
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }
    }

    @Override
    public boolean userCreated() {
        return true;
    }
}

