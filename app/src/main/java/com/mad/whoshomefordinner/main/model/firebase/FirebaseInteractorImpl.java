package com.mad.whoshomefordinner.main.model.firebase;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;

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

    private static final String TAG = "Who's Home For Dinner" ;
    private static final String NO_DATA = "Database error";

    private static final String DB_USER_REF = "User's";

    private static final String NAME_DB = "Name";
    private static final String EMAIL_DB = "Email";
    private static final String GROUP_DB = "Groups";

    public FirebaseInteractorImpl(FirebaseAuth auth, Activity context, DatabaseReference WHFDRef) {
        mAuth = auth;
        mContext = context;

        mFirebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null) {
            mUserID = mAuth.getCurrentUser().getUid();
            mWHFDRef2 = WHFDRef;
            mUserRef2 = mWHFDRef2.child(DB_USER_REF).child(mUserID);

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
                        if (NAME_DB.equals(item.getKey())) {
                            name = item.getValue().toString();
                        } else if (EMAIL_DB.equals(item.getKey())) {
                            email = item.getValue().toString();
                        } else if (GROUP_DB.equals(item.getKey())) {
                            Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                            for (DataSnapshot data : groupSnapShot) {
                                String temp = data.getKey().toString();
                                groupTemp.add(temp);
                            }
                        }
                    }
                }
                 else {
                    Log.d(TAG, NO_DATA);
                }

                mUser = new User(mUserID, name, email, groupTemp);
                userCreated();
                mMainPresenter.userCreatedYay();
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

    @Override
    public User getUser() {
        return mUser;
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

}
