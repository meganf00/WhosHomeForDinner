package com.mad.whoshomefordinner.fragments.home;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.main.activity.MainActivity;
import com.mad.whoshomefordinner.main.view.MainView;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String mUserID;
    private String mUserName;
    private String name;
    private String email;
    private List<Group> groups = new ArrayList<>();
    private User user;

    List<String> groupTemp;

    RecyclerView recycle;

    TextView mWelcomeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        String userID = arguments.getString("userID");
        String userName = arguments.getString("userMail");
        String userEmail = arguments.getString("userMail");
        String userGroups = arguments.getString("userGroups");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recycle = view.findViewById(R.id.home_groups);

        getActivity().setTitle(getString(R.string.title_home));

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            mUserID = mAuth.getCurrentUser().getUid().toString();
            mWHFDRef = FirebaseDatabase.getInstance().getReference();
            mUserRef = mWHFDRef.child("User's").child(mUserID);


            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if ("Name".equals(item.getKey())) {
                                mUserName = item.getValue().toString();
                                name = item.getValue().toString();

                            } else {
                                Log.d("TAG", "null");
                            }

                            mWelcomeText.setText(getString(R.string.welcome_txt_hey) + mUserName + getString(R.string.welcome_txt_rundown));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        mWelcomeText = view.findViewById(R.id.welcome_text);

        mWelcomeText.setText(getString(R.string.welcome_txt_hey) + mUserName + getString(R.string.welcome_txt_rundown));


        if (mAuth.getCurrentUser() != null) {


        }


//
//        UserGroupSumAdapter recyclerAdapter = new UserGroupSumAdapter(getContext(), groups , user);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(this.getActivity(),2);
        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        //recycle.setAdapter(recyclerAdapter);

        return view;
    }

}
