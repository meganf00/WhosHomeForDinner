package com.mad.whoshomefordinner.groupView.model;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenrerImpl;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Megan on 31/5/18.
 */

public class GroupViewFirebaseInteractorImpl implements GroupViewFirebaseInteractor {

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
    private User mUser;

    private String mGroupID;
    private String mGroupName;
    private List<String> mGroupMembers = new ArrayList<>();
    private List<String> mGroupWeekDays;
    private String mGroupDay;
    private String mGroupAllocatedCook;
    private String mGroupMeal;
    private String mGroupDeadline;
    Group mGroup;

    public Group group;

    private GroupViewPresenrerImpl mGroupViewPresenrer;

    private int count = 0;

    public GroupViewFirebaseInteractorImpl(FirebaseAuth auth, DatabaseReference WHFDRef, String groupID) {
        mAuth = auth;

        mFirebaseUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = WHFDRef;
        mUserRef = mWHFDRef.child("User's").child(mUserID);
        mGroupRef = mWHFDRef.child("Groups");
        mGroupID = groupID;

    }

    @Override
    public void getPresenter(GroupViewPresenter mainPresenter) {
        mGroupViewPresenrer = mainPresenter.getPresenter();
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public Group getGroup() {
        return mGroup;
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
                mGroupViewPresenrer.userCreated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
    }

    public void createGroup(){


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




            mGroupRef.child(mGroupID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.getKey().equals("Name")) {
                                mGroupName = item.getValue().toString();
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
                        mGroup = new Group(mGroupID, mGroupName, mGroupMembers, mGroupDay, mGroupAllocatedCook, mGroupMeal, mGroupDeadline);
                    }

                    mGroupViewPresenrer.groupCreated();


                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });









    }

    @Override
    public boolean userCreated() {
        return true;
    }
}

