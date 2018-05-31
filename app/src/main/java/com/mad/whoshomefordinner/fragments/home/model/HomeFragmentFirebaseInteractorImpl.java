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
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) weekDay = "Monday";
        else if (Calendar.TUESDAY == dayOfWeek) weekDay = "Tuesday";
        else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "Wednesday";
        else if (Calendar.THURSDAY == dayOfWeek) weekDay = "Thursday";
        else if (Calendar.FRIDAY == dayOfWeek) weekDay = "Friday";
        else if (Calendar.SATURDAY == dayOfWeek) weekDay = "Saturday";
        else if (Calendar.SUNDAY == dayOfWeek) weekDay = "Sunday";

        mGroupDay = weekDay;


            for (String groupID : mGroupIDs) {
                mGroupID = groupID;
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
                                                    mAllocatedCooksList.add(data2.getValue().toString());
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
                            group = new Group(mGroupID, mGroupName, mGroupMembers, mGroupDay, mGroupAllocatedCook, mGroupMeal, mGroupDeadline);
                        }

                        mGroups.add(group);

                        count += 1;


                        if (mGroupIDs.size() == count) {

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

    public void generateCookNames() {
        
    }

    @Override
    public List<String> getAllocatedCooks() {
        return mAllocatedCooksList;
    }
}
