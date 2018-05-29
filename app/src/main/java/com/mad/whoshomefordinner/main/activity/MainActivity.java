package com.mad.whoshomefordinner.main.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.whoshomefordinner.login.activity.LoginActivity;
import com.mad.whoshomefordinner.base.BaseActivity;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.fragments.group.GroupFragment;
import com.mad.whoshomefordinner.fragments.home.HomeFragment;
import com.mad.whoshomefordinner.fragments.notifications.NotificationsFragment;
import com.mad.whoshomefordinner.fragments.schedule.ScheduleFragment;
import com.mad.whoshomefordinner.fragments.settings.SettingsFragment;
import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private View mNavHeader;

    private FirebaseAuth mAuth;
    private MainPresenterImpl mHomePresenter;
    private DatabaseReference mWHFDRef;

    private User mUser;

    @BindView(R.id.home_progress_bar)
    ProgressBar userShowProgressBar;

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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNavHeader = navigationView.getHeaderView(0);

        navigationView.setCheckedItem(R.id.nav_home);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sign_out) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void displaySelectedFragment(Fragment fragment, User user) {
        Bundle bundle = new Bundle();
        bundle.putString("userID", mUser.getId().toString());
        bundle.putString("userName", mUser.getName().toString());
        bundle.putString("userMail", mUser.getEmail().toString());
        bundle.putString("userGroups", mUser.getGroups().toString());
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
