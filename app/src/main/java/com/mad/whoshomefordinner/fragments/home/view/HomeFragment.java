package com.mad.whoshomefordinner.fragments.home.view;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.fragments.home.model.firebase.UserGroupSumAdapter;
import com.mad.whoshomefordinner.fragments.home.presenter.HomeFragmentPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;
import com.mad.whoshomefordinner.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 * Home Fragment is the corresponding java class for the fragment view,
 * which sets up the UI of the fragment, and communciates with the
 * presenter of this class. It shows a small summary of the each
 * group the user is a member of, and allows them to change their home response
 * for dinner from this page.
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

    List<String> mAllocatedCooks;

    RecyclerView recycle;

    TextView mWelcomeText;

    ProgressBar mProgressBar;

    UserGroupSumAdapter mRecyclerAdapter;

    ProgressBar mHomeProgressBar;

    private LinearLayout mHomeGroupsView;

    private TextView mHomeStatusTxt;

    private int mPosition;
    private View mView;

    private static final String USER_ID = "userID";
    private static final String DB_REFERENCE_USERS = "User's";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.title_home));


        Bundle arguments = getArguments();
        String userID = arguments.getString(USER_ID);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recycle = view.findViewById(R.id.home_groups);



        mProgressBar = view.findViewById(R.id.home_progress_bar);
        mWelcomeText = view.findViewById(R.id.welcome_text);


        showProgressDialog();

        mAuth = FirebaseAuth.getInstance();

        mUserID = mAuth.getCurrentUser().getUid().toString();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mWHFDRef.child(DB_REFERENCE_USERS).child(mUserID);

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

        generateAllocatedCooks();
    }

    /**
     * Calls presenter to generate the allocated cooks for the week
     */
    private void generateAllocatedCooks() {
        mHomeFragmentPresenter.setUpAllocatedCooks();
    }

    @Override
    public void updateRow() {

        if (getString(R.string.yes_txt).equals(mHomeStatusTxt.getText())) {
            mHomeStatusTxt.setText(R.string.no_txt);
        } else if (getString(R.string.no_txt).equals(mHomeStatusTxt.getText())) {
            mHomeStatusTxt.setText(R.string.yes_txt);
        }

        mHomeProgressBar.setVisibility(View.GONE);
        mHomeGroupsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getAllocatedCooks() {
        mAllocatedCooks = mHomeFragmentPresenter.getAllocatedCooksNames();
        initiateView();
    }


    @Override
    public void initiateView() {

        mUserName = mUser.getName();

        mWelcomeText.setText(getString(R.string.welcome_txt_hey)  + mUserName);

        mRecyclerAdapter = new UserGroupSumAdapter(getContext(), groups , mUser, mAllocatedCooks);
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getActivity(),1);
        recycle.setLayoutManager(layoutManager);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(mRecyclerAdapter);

        hideProgressDialog();

        recycle.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mHomeStatusTxt = view.findViewById(R.id.home_status);

                mView = view;
                mPosition = position;
                mHomeProgressBar = view.findViewById(R.id.home_status_progress);
                mHomeGroupsView = view.findViewById(R.id.body);
                mHomeProgressBar.setVisibility(View.VISIBLE);
                mHomeGroupsView.setVisibility(View.GONE);
                updateHomeStatus(view, position);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void createAllocatedCookToast() {
        Toast.makeText(getActivity(), R.string.allocated_cook_txt,
                Toast.LENGTH_LONG).show();
        mHomeProgressBar.setVisibility(View.GONE);
        mHomeGroupsView.setVisibility(View.VISIBLE);
    }


    /**
     * Calls methods in presenter to update the current home status
     * @param view
     * @param position
     */
    public void updateHomeStatus(View view, int position) {
        mHomeFragmentPresenter.handleRowClick(position);

    }

    @Override
    public void showPastDeadlineToast() {
        Toast.makeText(getActivity(), R.string.deadline_has_past_txt,
                Toast.LENGTH_LONG).show();
        mHomeProgressBar.setVisibility(View.GONE);
        mHomeGroupsView.setVisibility(View.VISIBLE);
    }

}
