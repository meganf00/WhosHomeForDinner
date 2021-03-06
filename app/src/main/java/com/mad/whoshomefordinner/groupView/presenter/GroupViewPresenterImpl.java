package com.mad.whoshomefordinner.groupView.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mad.whoshomefordinner.groupView.model.GroupViewFirebaseInteractorImpl;
import com.mad.whoshomefordinner.groupView.view.GroupViewView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 31/5/18.
 *
 * GroupViewPresenterImpl is a class, which implements the GroupViewPresenter
 * and is used as a communication means between the GroupVIewView and
 * the GroupViewPresenter.
 */

public class GroupViewPresenterImpl implements GroupViewPresenter{

    FirebaseAuth mAuth;
    private GroupViewView mGroupViewView;
    private Activity mContext;
    private GroupViewFirebaseInteractorImpl mGroupViewFirebaseInteractor;
    private DatabaseReference mWHFDRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String mGroupID;


    /**
     * Constructor to create an instance of the GroupViewPresenterImpl
     * for communication between the view and the model.
     *
     * @param auth
     * @param WHFDRef
     * @param groupdID
     */
    public GroupViewPresenterImpl(FirebaseAuth auth, DatabaseReference WHFDRef, String groupdID){
        mAuth = auth;
        mWHFDRef = WHFDRef;
        mGroupID = groupdID;
    }

    @Override
    public void setUpInteractor() {
        mGroupViewFirebaseInteractor = new GroupViewFirebaseInteractorImpl(mAuth, mWHFDRef, mGroupID);
    }

    @Override
    public GroupViewPresenterImpl getPresenter() {
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
    public void setUpAllocatedCook() {
        mGroupViewFirebaseInteractor.generateAllocatedCook();
    }

    @Override
    public boolean isUserAllocatedCook() {
        return mGroupViewFirebaseInteractor.checkUserIsCook();
    }

    @Override
    public void allocatedCookNameGenerated() {
        mGroupViewView.getAllocatedCookName();
    }

    @Override
    public String getAllocatedCook() {
        return mGroupViewFirebaseInteractor.getAllocatedCookName();
    }

    @Override
    public boolean userIsHome() {
        return mGroupViewFirebaseInteractor.isUserHome();
    }

    @Override
    public void setNextDate() {
        mGroupViewFirebaseInteractor.findNextCookDay();
    }

    @Override
    public void cookingDaysGenerated() {
        mGroupViewView.getNextCookingDay();
    }

    @Override
    public String getNextCookingDay() throws RuntimeException, NullPointerException {
        return mGroupViewFirebaseInteractor.getNextCookDay();
    }

    @Override
    public int getNoMembers() {
        return mGroupViewFirebaseInteractor.getMemberCount();
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

    /**
     * Gets the current group selected and returns it
     * @return Group
     */
    public Group getGroup() {
        return mGroupViewFirebaseInteractor.getGroup();
    }

    /**
     * Creates a group based on the current one selected
     */
    public void groupCreated() {
        mGroupViewView.getGroup();
    }


    @Override
    public void userCreated() {
        mGroupViewView.setUpUser();
    }


}
