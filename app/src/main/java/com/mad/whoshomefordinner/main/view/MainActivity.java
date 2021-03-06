package com.mad.whoshomefordinner.main.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.login.view.LoginActivity;
import com.mad.whoshomefordinner.base.BaseActivity;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.fragments.group.view.GroupFragment;
import com.mad.whoshomefordinner.fragments.home.view.HomeFragment;
import com.mad.whoshomefordinner.fragments.notifications.NotificationsFragment;
import com.mad.whoshomefordinner.fragments.schedule.ScheduleFragment;
import com.mad.whoshomefordinner.fragments.settings.SettingsFragment;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created 05/05/18
 *
 * MainActivity corresponds to the Main layout and is the starting activity. It
 * sets up the UI for the activity and communicates with the presenter. It is the main
 * view for the side navigation drawer menu and displays fragments.
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private static String USER_ID = "userID";

    private View mNavHeader;

    private FirebaseAuth mAuth;
    private MainPresenterImpl mHomePresenter;
    private DatabaseReference mWHFDRef;

    private User mUser;

    @BindView(R.id.home_progress_bar)
    ProgressBar userShowProgressBar;

    //@BindView(R.id.userNameHeader)
    TextView mUserNameHeader;

    //@BindView(R.id.emailHeaderTxt)
    TextView mEmailHeaderTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);



        mAuth = FirebaseAuth.getInstance();
        mWHFDRef = FirebaseDatabase.getInstance().getReference();
        mHomePresenter = new MainPresenterImpl(mAuth, this, mWHFDRef);

        mHomePresenter.attachView(this);

        mHomePresenter.setUpInteractor();
        mHomePresenter.connectWithInteractor();

        mHomePresenter.checkIfSignedIn();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNavHeader = navigationView.getHeaderView(0);

        navigationView.setCheckedItem(R.id.nav_home);



        mUserNameHeader = mNavHeader.findViewById(R.id.userNameHeader);
        mEmailHeaderTxt = mNavHeader.findViewById(R.id.emailHeaderTxt);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new HomeFragment();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();

        } else if (id == R.id.nav_groups) {
            fragment = new GroupFragment();

        } else if (id == R.id.nav_schedule) {
            fragment = new ScheduleFragment();

        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();

        } else if (id == R.id.nav_sign_out) {
            mHomePresenter.signOut();
            fragment = null;
        }

        if (fragment != null) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            displaySelectedFragment(fragment, mUser);
        }

        return true;
    }

    /**
     * Displays the selected fragment, selected from navigation drawer
     * @param fragment
     * @param user
     */
    private void displaySelectedFragment(Fragment fragment, User user) {
        Bundle bundle = new Bundle();

        bundle.putString(USER_ID, mUser.getId().toString());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void goToLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void setUpUser() {
        mUser = mHomePresenter.getCurrentUser();
    }

    @Override
    public void showProgressDialog() {
        userShowProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        userShowProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUpFragment() {
        setUpUser();

        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment, mUser);

        mUserNameHeader.setText(mUser.getName());
        mEmailHeaderTxt.setText(mUser.getEmail());

        hideProgressDialog();

    }


    @Override
    protected void onStop() {
        super.onStop();
        mHomePresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.detachView();
    }


}
