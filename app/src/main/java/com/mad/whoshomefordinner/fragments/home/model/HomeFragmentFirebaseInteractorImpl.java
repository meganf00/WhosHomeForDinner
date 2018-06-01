package com.mad.whoshomefordinner.fragments.home.model;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenter;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Megan on 29/5/18.
 */

public class HomeFragmentFirebaseInteractorImpl implements HomeFragmentFirebaseInteractor{

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

    private List<String> mAllocatedCooksList = new ArrayList<>();

    public Group group;

    private HomeFragmentPresenterImpl mHomeFragmentPresenter;

    private int count = 0;
    private int groupCount = 0;

    public HomeFragmentFirebaseInteractorImpl(FirebaseAuth auth, DatabaseReference WHFDRef) {
        mAuth = auth;

        mFirebaseUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = WHFDRef;
        mUserRef = mWHFDRef.child("User's").child(mUserID);
        mGroupRef = mWHFDRef.child("Groups");
    }

    @Override
    public void getPresenter(HomeFragmentPresenter mainPresenter) {
        mHomeFragmentPresenter = mainPresenter.getPresenter();
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
                        if ("Name".equals(item.getKey())) {
                            mUserName = item.getValue().toString();
                        } else if ("Email".equals(item.getKey())) {
                            mUserEmail = item.getValue().toString();
                        } else if ("Groups".equals(item.getKey())) {
                            Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                            for (DataSnapshot data : groupSnapShot) {
                                String temp = data.getKey().toString();
                                mGroupIDs.add(temp);
                            }
                        }
                    }
                }
                else {
                    Log.d("TAG", "null");
                }

                mUser = new User(mUserID, mUserName, mUserEmail, mGroupIDs);
                userCreated();
                mHomeFragmentPresenter.userCreated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
    }

    public void createGroups(){

        String weekDay = "";
        Calendar c = Calendar.getInstance();
        mGroupDay = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());


            for (final String groupID : mGroupIDs) {
                //mGroupID = groupID;
                mGroupRef.child(groupID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for (DataSnapshot item : dataSnapshot.getChildren()) {
                                if (item.getKey().equals("Name")) {
                                    mGroupName = item.getValue().toString();
                                    break;
                                } else if (item.getKey().equals("Current Week")) {
                                    Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                                    for (DataSnapshot data : groupSnapShot) {
                                        if (data.getKey().equals(mGroupDay)) {
                                            Iterable<DataSnapshot> daySnapShot = data.getChildren();
                                            for (DataSnapshot data2 : daySnapShot) {
                                                if (data2.getKey().equals("Allocated cook")) {
                                                    mGroupAllocatedCook = data2.getValue().toString();
                                                } else if (data2.getKey().equals("Deadline")) {
                                                    mGroupDeadline = data2.getValue().toString();
                                                } else if (data2.getKey().equals("Meal")) {
                                                    mGroupMeal = data2.getValue().toString();
                                                } else if (data2.getKey().equals("Home")) {
                                                    Iterable<DataSnapshot> homeSnapShot = data2.getChildren();
                                                    for (DataSnapshot data3 : homeSnapShot) {
                                                        if (data3.getValue().equals("True")) {
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


                        if (groupCount >= mGroupIDs.size()) {

                            mHomeFragmentPresenter.groupsCreated();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //TODO Add handling stuff here!!
                    }
                });
            }
    }



    @Override
    public boolean userCreated() {
        return true;
    }

    @Override
    public void generateCookNames() {
        count = 0;
        for (Group group : mGroups) {
            DatabaseReference nameRef = mWHFDRef.child("User's").child(group.getAllocatedCook());
            nameRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()){
                            if ("Name".equals(item.getKey())){
                                mAllocatedCooksList.add(item.getValue().toString());
                                break;
                            }
                        }
                        count += 1;
                    }

                    if (count == mGroupIDs.size()) {
                        mHomeFragmentPresenter.allocatedCooksGenerated();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void updateHomeStatus(int position) {
        String groupID = mGroups.get(position).getId();
        DatabaseReference groupRef = mWHFDRef.child("Groups").child(groupID).child("Current Week")
                .child(mGroupDay).child("Home");
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        if (mUserID.equals(item.getKey().toString())) {
                            if ("True".equals(item.getValue().toString())) {
                                item.getRef().setValue("False");
                                break;
                            } else if ("False".equals(item.getValue().toString())) {
                                item.getRef().setValue("True");
                                break;
                            }
                        }
                    }
                }
                mHomeFragmentPresenter.rowClickFinished();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public List<String> getAllocatedCooks() {
        return mAllocatedCooksList;
    }
}
