package com.mad.whoshomefordinner.viewgroupmembers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mad.whoshomefordinner.R;

/**
 * Created 05/06/18
 *
 * ViewGroupMembersActivity corresponds to the ViewGroupMembers layout and
 * displays a list of group members in the selected group
 */

public class ViewGroupMembersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
