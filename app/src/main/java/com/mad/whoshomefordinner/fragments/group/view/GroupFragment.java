package com.mad.whoshomefordinner.fragments.group.view;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.fragments.group.GroupViewAdapter;
import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements GroupFragmentView {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mGroupsRef;
    private FirebaseAuth mAuth;
    private GroupFragmentPresenterImpl mGroupFragmentPresenter;

    private String mUserID;
    private String mUserName;
    private List<Group> mGroups = new ArrayList<>();
    private User mUser;

    RecyclerView mRecyclerView;

    TextView mTitleTxt;

    ProgressBar mProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_groups));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        Bundle arguments = getArguments();
        String userID = arguments.getString("userID");

        mRecyclerView = view.findViewById(R.id.groups_recycler);
        mProgressBar = view.findViewById(R.id.group_progress_bar);
        mTitleTxt = view.findViewById(R.id.title_txt);

        showProgressDialog();

        mAuth = FirebaseAuth.getInstance();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child("User's").child(mUserID);
        mGroupsRef = mWHFDRef.child("Groups");

        mGroupFragmentPresenter = new GroupFragmentPresenterImpl(mAuth, mWHFDRef);

        mGroupFragmentPresenter.attachView(this);
        mGroupFragmentPresenter.setUpInteractor();
        mGroupFragmentPresenter.connectWithInteractor();

        mGroupFragmentPresenter.setUpUser();

        return view;
    }

    @Override
    public void setUpUser() {
        mUser = mGroupFragmentPresenter.getCurrentUser();
    }

    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTitleTxt.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.GONE);
        mTitleTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpFragment() {
        mUser = mGroupFragmentPresenter.getCurrentUser();
        setUpGroups();
    }

    @Override
    public void setUpGroups() {
        mGroupFragmentPresenter.setUpGroups();
    }

    @Override
    public void getGroups() {
        mGroups = mGroupFragmentPresenter.getGroups();

        if (mGroups != null) {
            initiateView();
        }
    }

    public void initiateView() {
        mTitleTxt.setText("Your Groups");

        GroupViewAdapter recyleAdapter = new GroupViewAdapter(getContext(), mGroups);
        RecyclerView.LayoutManager recyle = new GridLayoutManager(this.getActivity(), 1);
        mRecyclerView.setLayoutManager(recyle);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(recyleAdapter);

        hideProgressDialog();
    }
}
