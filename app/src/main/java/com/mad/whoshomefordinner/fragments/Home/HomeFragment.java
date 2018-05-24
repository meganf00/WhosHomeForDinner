package com.mad.whoshomefordinner.fragments.Home;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.mad.whoshomefordinner.Home.HomePresenterImpl;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.User;

import java.sql.DatabaseMetaData;

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
    private String mUserName;


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

        //DatabaseReference ref = mUserRef.equalTo(mUserID);


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
                            }

                        }
                    } else {
                        Log.d("TAG", "null");
                    }

                    mWelcomeText.setText(getString(R.string.welcome_txt_hey) + mUserName + getString(R.string.welcome_txt_rundown));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "DB Failed", Toast.LENGTH_LONG).show();
                }
            });
        }

        mWelcomeText = (TextView)view.findViewById(R.id.welcome_text);

        mWelcomeText.setText(getString(R.string.welcome_txt_hey) + mUserName + getString(R.string.welcome_txt_rundown));

        return view;
    }

}
