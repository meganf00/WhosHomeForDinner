package com.mad.whoshomefordinner.fragments.Home;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.Home.HomeActivity;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.User;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String mUserID;


    TextView mWelcomeText;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        getActivity().setTitle(getString(R.string.title_home));
        mWHFDRef = FirebaseDatabase.getInstance().getReference("whos-home-for-dinner");
        mUserRef = mWHFDRef.child("User's");
        mAuth = FirebaseAuth.getInstance();
        mUserID = mAuth.getCurrentUser().getUid();

//        userid = mAuth.getCurrentUser().getUid();

//        mDBRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "DB Failed", Toast.LENGTH_LONG).show();
//            }
//        });

        mWelcomeText = (TextView)view.findViewById(R.id.welcome_text);

        mWelcomeText.setText(mUserID);

        return view;
    }

}
