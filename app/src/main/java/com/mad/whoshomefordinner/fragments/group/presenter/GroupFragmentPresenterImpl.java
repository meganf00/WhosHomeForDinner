package com.mad.whoshomefordinner.fragments.group.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.fragments.group.model.GroupFragmentFirebaseInteractorImpl;
import com.mad.whoshomefordinner.fragments.group.view.GroupFragment;
import com.mad.whoshomefordinner.fragments.group.view.GroupFragmentView;
import com.mad.whoshomefordinner.fragments.home.model.HomeFragmentFirebaseInteractorImpl;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 30/5/18.
 */

public class GroupFragmentPresenterImpl implements GroupFragmentPresenter {

    FirebaseAuth mAuth;
    private GroupFragmentView mGroupFragmentView;
    private Activity mContext;
    private GroupFragmentFirebaseInteractorImpl mGroupFragmentFirebaseInteractor;
    private DatabaseReference mWHFDRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public GroupFragmentPresenterImpl(FirebaseAuth auth, DatabaseReference WHFDRef){
        mAuth = auth;
        mWHFDRef = WHFDRef;
    }

    @Override
    public void attachView(GroupFragment view) {
        mGroupFragmentView = view;
    }

    @Override
    public void detachView() {
        mGroupFragmentView = null;
    }

    @Override
    public User getCurrentUser() {
        return mGroupFragmentFirebaseInteractor.getUser();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void userCreated() {
        mGroupFragmentView.setUpFragment();
    }

    @Override
    public void setUpInteractor() {
        mGroupFragmentFirebaseInteractor = new GroupFragmentFirebaseInteractorImpl(mAuth, mWHFDRef);
    }

    @Override
    public GroupFragmentPresenterImpl getPresenter() {
        return this;
    }

    @Override
    public void connectWithInteractor() {
        mGroupFragmentFirebaseInteractor.getPresenter(this);
    }

    @Override
    public void setUpUser() {
        mGroupFragmentFirebaseInteractor.createUser();
    }

    public void setUpGroups() {
        mGroupFragmentFirebaseInteractor.createGroups();
    }

    public List<Group> getGroups() {
        return mGroupFragmentFirebaseInteractor.getGroups();
    }

    public void groupsCreated() {
        mGroupFragmentView.getGroups();
    }
}
