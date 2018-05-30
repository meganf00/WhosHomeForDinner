package com.mad.whoshomefordinner.fragments.home.view;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.fragments.home.UserGroupSumAdapter;
import com.mad.whoshomefordinner.fragments.home.model.HomeFragmentFirebaseInteractor;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.main.activity.MainActivity;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseView, HomeFragmentView {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private HomeFragmentPresenterImpl mHomeFragmentPresenter;

    private String mUserID;
    private String mUserName;

    private List<Group> groups = new ArrayList<>();
    private User mUser;

    List<String> groupTemp;

    RecyclerView recycle;

    TextView mWelcomeText;

    ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.title_home));


        Bundle arguments = getArguments();
        String userID = arguments.getString("userID");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recycle = view.findViewById(R.id.home_groups);



        mProgressBar = view.findViewById(R.id.home_progress_bar);
        mWelcomeText = view.findViewById(R.id.welcome_text);


        showProgressDialog();

        mAuth = FirebaseAuth.getInstance();

        mUserID = mAuth.getCurrentUser().getUid().toString();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child("User's").child(mUserID);

        mHomeFragmentPresenter = new HomeFragmentPresenterImpl(mAuth, mWHFDRef);

        mHomeFragmentPresenter.attachView(this);
        mHomeFragmentPresenter.setUpInteractor();
        mHomeFragmentPresenter.connectWithInteractor();

        mHomeFragmentPresenter.setUpUser();

        return view;
    }

    @Override
    public void setUpUser() {
        mUser = mHomeFragmentPresenter.getCurrentUser();
    }

    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
        mWelcomeText.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.GONE);
        mWelcomeText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpFragment() {
        mUser = mHomeFragmentPresenter.getCurrentUser();
        setUpGroups();

    }

    @Override
    public void setUpGroups() {
        mHomeFragmentPresenter.setUpGroups();
    }

    @Override
    public void getGroups(){
        groups = mHomeFragmentPresenter.getGroups();

        if (groups != null) {
            initiateView();
        }
    }

    public void initiateView() {

        mUserName = mUser.getName();

        mWelcomeText.setText(getString(R.string.welcome_txt_hey) + mUserName + getString(R.string.welcome_txt_rundown));

        UserGroupSumAdapter recyclerAdapter = new UserGroupSumAdapter(getContext(), groups , mUser);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(this.getActivity(),2);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

        hideProgressDialog();

    }



}
