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
import java.util.List;

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

    private HomeFragmentPresenterImpl mHomeFragmentPresenter;

    public HomeFragmentFirebaseInteractorImpl(FirebaseAuth auth, DatabaseReference WHFDRef) {
        mAuth = auth;

        mFirebaseUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = WHFDRef;
        mUserRef = mWHFDRef.child("User's").child(mUserID);
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
        return null;
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

    @Override
    public boolean userCreated() {
        return true;
    }
}
