package com.mad.whoshomefordinner.main.model.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.main.activity.MainActivity;
import com.mad.whoshomefordinner.main.presenter.MainPresenter;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * Created by Megan on 27/5/18.
 */

public class FirebaseInteractorImpl implements FirebaseInteractor {

    FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    private String mUserID;
    private Activity mContext;

    private DatabaseReference mWHFDRef2;
    public DatabaseReference mUserRef2;

    private String name;
    private String email;
    private List<String> groupTemp = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private User mUser;

    private MainPresenterImpl mMainPresenter;


    public FirebaseInteractorImpl(FirebaseAuth auth, Activity context, DatabaseReference WHFDRef) {
        mAuth = auth;
        mContext = context;

        mFirebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null) {
            mUserID = mAuth.getCurrentUser().getUid();
            mWHFDRef2 = WHFDRef;
            mUserRef2 = mWHFDRef2.child("User's").child(mUserID);

        }

    }

    @Override
    public void getPresenter(MainPresenterImpl mainPresenter){
        mMainPresenter = mainPresenter.getPresenter();
    }

    @Override
    public void createUser() {

        mUserRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if ("Name".equals(item.getKey())) {
                            name = item.getValue().toString();
                        } else if ("Email".equals(item.getKey())) {
                            email = item.getValue().toString();
                        } else if ("Groups".equals(item.getKey())) {
                            Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                            for (DataSnapshot data : groupSnapShot) {
                                String temp = data.getKey().toString();
                                groupTemp.add(temp);
                            }
                        }
                    }
                }
                 else {
                    Log.d("TAG", "null");
                }

                mUser = new User(mUserID, name, email, groupTemp);
                userCreated();
                mMainPresenter.userCreatedYay();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });

        //createGroups(groupTemp);



    }

    @Override
    public boolean userCreated() {
            return true;
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
    public boolean isSignedIn() {
        if (mAuth.getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logOut() {
        mAuth.signOut();
    }

    public void createGroups(final List<String> groupTempList) {
        DatabaseReference groupRef = mWHFDRef2.child("Groups");
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
//                        for (String id : groupTemp){
//                            if (id == item.getKey()) {
//                                groups.add(item.getValue(Group.class));
//                            }
//                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
