package com.mad.whoshomefordinner.fragments.group.view;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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
import com.mad.whoshomefordinner.fragments.group.model.GroupViewAdapter;
import com.mad.whoshomefordinner.fragments.group.presenter.GroupFragmentPresenterImpl;
import com.mad.whoshomefordinner.groupView.view.GroupViewActivity;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;
import com.mad.whoshomefordinner.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * GroupFragment is the corresponding java class for the fragment view,
 * which sets up the UI of the fragment, and communciates with the
 * presenter of this class. It shows a list of groups the user is
 * a member of.
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

    private int mPosition;

    private static final String INTENT_GROUP_ID = "groupID";
    private static String USER_ID = "userID";
    private static final String DB_USER_REF = "User's";
    private static final String DB_GROUP_REF = "Groups";

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
        String userID = arguments.getString(USER_ID);

        mRecyclerView = view.findViewById(R.id.groups_recycler);
        mProgressBar = view.findViewById(R.id.group_progress_bar);
        mTitleTxt = view.findViewById(R.id.title_txt);

        showProgressDialog();

        mAuth = FirebaseAuth.getInstance();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child(DB_USER_REF).child(mUserID);
        mGroupsRef = mWHFDRef.child(DB_GROUP_REF);

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


    /**
     * Initialises and creates the view. It is called after acquiring all required data
     */
    public void initiateView() {
        mTitleTxt.setText(R.string.your_groups);

        GroupViewAdapter groupViewAdapter = new GroupViewAdapter(getContext(), mGroups);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(groupViewAdapter);

        hideProgressDialog();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
                Group group = mGroups.get(position);


                Intent intent = new Intent(getContext(), GroupViewActivity.class);
                intent.putExtra(INTENT_GROUP_ID, group.getId());
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
