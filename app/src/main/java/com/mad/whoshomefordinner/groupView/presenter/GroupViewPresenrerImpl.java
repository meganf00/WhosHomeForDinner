package com.mad.whoshomefordinner.groupView.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.groupView.model.GroupViewFirebaseInteractorImpl;
import com.mad.whoshomefordinner.groupView.view.GroupViewView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 31/5/18.
 */

public class GroupViewPresenrerImpl implements GroupViewPresenter{

    FirebaseAuth mAuth;
    private GroupViewView mGroupViewView;
    private Activity mContext;
    private GroupViewFirebaseInteractorImpl mGroupViewFirebaseInteractor;
    private DatabaseReference mWHFDRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String mGroupID;

    public GroupViewPresenrerImpl(FirebaseAuth auth, DatabaseReference WHFDRef, String groupdID){
        mAuth = auth;
        mWHFDRef = WHFDRef;
        mGroupID = groupdID;
    }

    @Override
    public void setUpInteractor() {
        mGroupViewFirebaseInteractor = new GroupViewFirebaseInteractorImpl(mAuth, mWHFDRef, mGroupID);
    }

    @Override
    public GroupViewPresenrerImpl getPresenter() {
        return this;
    }

    @Override
    public void connectWithInteractor() {
        mGroupViewFirebaseInteractor.getPresenter(this);
    }

    @Override
    public void setUpUser() {
        mGroupViewFirebaseInteractor.createUser();
    }


    @Override
    public void attachView(GroupViewView view) {
        mGroupViewView = view;
    }

    @Override
    public void detachView() {
        mGroupViewView = null;
    }

    @Override
    public User getCurrentUser() {
        return mGroupViewFirebaseInteractor.getUser();
    }

    public void setUpGroup() {
        mGroupViewFirebaseInteractor.createGroup();
    }

    public Group getGroup() {
        return mGroupViewFirebaseInteractor.getGroup();
    }

    public void groupCreated() {
        mGroupViewView.getGroup();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void userCreated() {
        mGroupViewView.initiateView();
    }


}
