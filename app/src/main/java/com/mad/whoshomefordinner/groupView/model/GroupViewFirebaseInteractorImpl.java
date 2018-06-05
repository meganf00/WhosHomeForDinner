package com.mad.whoshomefordinner.groupView.model;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenterImpl;
import com.mad.whoshomefordinner.groupView.presenter.GroupViewPresenter;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Megan on 31/5/18.
 */

public class GroupViewFirebaseInteractorImpl implements GroupViewFirebaseInteractor {

    FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    private String mUserID;
    private Activity mContext;

    private DatabaseReference mWHFDRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mGroupRef;

    private String mUserName;
    private String mUserEmail;
    private List<String> mGroupIDs = new ArrayList<>();
    private User mUser;

    private String mGroupID;
    private String mGroupName;
    private List<String> mGroupMembers = new ArrayList<>();
    private String mGroupDay;
    private String mGroupAllocatedCook;
    private String mGroupMeal;
    private String mGroupDeadline;
    Group mGroup;
    private String mAllocatedCookName;

    public Group group;

    private GroupViewPresenterImpl mGroupViewPresenter;

    private Map<String, Boolean> mWeekCookingStatus = new HashMap<String, Boolean>();
    private int mGroupListNo = 0;


    private static final String TAG = "Who's Home For Dinner" ;
    private static final String NO_DATA = "Database error";
    private static final String DB_USER_REF = "User's";
    private static final String DB_GROUP_REF = "Groups";
    private static final String NAME_DB = "Name";
    private static final String EMAIL_DB = "Email";
    private static final String GROUP_DB = "Groups";
    private static final String WEEK_DB = "Current Week";
    private static final String ALL_COOK_DB = "Allocated cook";
    private static final String DEADLINE_DB = "Deadline";
    private static final String MEAL_DB = "Meal";
    private static final String HOME_DB = "Home";
    private static final String TRUE_DB = "True";
    private static final String FALSE_DB = "False";


    public GroupViewFirebaseInteractorImpl(FirebaseAuth auth, DatabaseReference WHFDRef, String groupID) {
        mAuth = auth;

        mFirebaseUser = mAuth.getCurrentUser();
        mUserID = mAuth.getCurrentUser().getUid();
        mWHFDRef = WHFDRef;
        mUserRef = mWHFDRef.child(DB_USER_REF).child(mUserID);
        mGroupRef = mWHFDRef.child(DB_GROUP_REF);
        mGroupID = groupID;

    }

    @Override
    public void getPresenter(GroupViewPresenter mainPresenter) {
        mGroupViewPresenter = mainPresenter.getPresenter();
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public Group getGroup() {
        return mGroup;
    }

    @Override
    public void createUser() {
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (NAME_DB.equals(item.getKey())) {
                            mUserName = item.getValue().toString();
                        } else if (EMAIL_DB.equals(item.getKey())) {
                            mUserEmail = item.getValue().toString();
                        } else if (GROUP_DB.equals(item.getKey())) {
                            Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                            for (DataSnapshot data : groupSnapShot) {
                                String temp = data.getKey().toString();
                                mGroupIDs.add(temp);
                            }
                        }
                    }
                }
                else {
                    Log.d(TAG, NO_DATA);
                }

                mUser = new User(mUserID, mUserName, mUserEmail, mGroupIDs);
                userCreated();
                mGroupViewPresenter.userCreated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
    }

    public void createGroup(){


        String weekDay = "";
        Calendar c = Calendar.getInstance();
        mGroupDay = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());





            mGroupRef.child(mGroupID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.getKey().equals(NAME_DB)) {
                                mGroupName = item.getValue().toString();
                            } else if (item.getKey().equals(WEEK_DB)) {
                                Iterable<DataSnapshot> groupSnapShot = item.getChildren();
                                for (DataSnapshot data : groupSnapShot) {
                                    if (data.getKey().equals(mGroupDay)) {
                                        Iterable<DataSnapshot> daySnapShot = data.getChildren();
                                        for (DataSnapshot data2 : daySnapShot) {
                                            if (data2.getKey().equals(ALL_COOK_DB)) {
                                                mGroupAllocatedCook = data2.getValue().toString();
                                            } else if (data2.getKey().equals(DEADLINE_DB)) {
                                                mGroupDeadline = data2.getValue().toString();
                                            } else if (data2.getKey().equals(MEAL_DB)) {
                                                mGroupMeal = data2.getValue().toString();
                                            } else if (data2.getKey().equals(HOME_DB)) {
                                                Iterable<DataSnapshot> homeSnapShot = data2.getChildren();
                                                for (DataSnapshot data3 : homeSnapShot) {
                                                    mGroupListNo += 1;
                                                    if (data3.getValue().equals(TRUE_DB)) {
                                                        String temp = data3.getKey().toString();
                                                        mGroupMembers.add(temp);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        mGroup = new Group(mGroupID, mGroupName, mGroupMembers, mGroupDay, mGroupAllocatedCook, mGroupMeal, mGroupDeadline);
                    }

                    mGroupViewPresenter.groupCreated();


                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });









    }

    @Override
    public boolean userCreated() {
        return true;
    }

    @Override
    public void generateAllocatedCook() {
        DatabaseReference cookNameRef = mWHFDRef.child(DB_USER_REF).child(mGroupAllocatedCook);
        cookNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (NAME_DB.equals(item.getKey())){
                            mAllocatedCookName = item.getValue().toString();
                            break;
                        }
                    }
                }
                mGroupViewPresenter.allocatedCookNameGenerated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean checkUserIsCook() {
        if (mUserID.equals(mGroupAllocatedCook)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getAllocatedCookName() {
        return mAllocatedCookName;
    }

    @Override
    public Boolean isUserHome() {
        boolean home = false;
        for (String member : mGroupMembers) {
            if (mUserID.equals(member)){
                home =  true;
                break;
            }
        }
        return home;
    }

    @Override
    public void findNextCookDay() {

        //Below code does not work- keeping for future reference

        mGroupRef.child(mGroupID).child(WEEK_DB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Iterable<DataSnapshot> weekDaysSnapshot = item.getChildren();
                        for (DataSnapshot day : weekDaysSnapshot) {
                            if ("Sunday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Sunday", true);
                                        } else {
                                            mWeekCookingStatus.put("Sunday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Monday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Monday", true);
                                        } else {
                                            mWeekCookingStatus.put("Monday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Tuesday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Tuesday", true);
                                        } else {
                                            mWeekCookingStatus.put("Tuesday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Wednesday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Wednesday", true);
                                        } else {
                                            mWeekCookingStatus.put("Wednesday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Thursday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Thursday", true);
                                        } else {
                                            mWeekCookingStatus.put("Thursday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Friday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Friday", true);
                                        } else {
                                            mWeekCookingStatus.put("Friday", false);
                                        }
                                    }
                                }
                                break;
                            } else if ("Saturday".equals(day.getKey())) {
                                Iterable<DataSnapshot> dayData = item.getChildren();
                                for (DataSnapshot data : dayData) {
                                    if ("AllocatedCook".equals(data.getKey())) {
                                        if (mUserID.equals(data.getValue())) {
                                            mWeekCookingStatus.put("Saturday", true);
                                        } else {
                                            mWeekCookingStatus.put("Saturday", false);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }

                mGroupViewPresenter.cookingDaysGenerated();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public String getNextCookDay() throws RuntimeException, NullPointerException {

        String day = "";


        //Below code does not work- keeping for future reference

//            if (mGroupDay == "Sunday") {
//
//                if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                }
//
//
//            } else if (mGroupDay.equals("Monday")) {
//
//                if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                }
//
//            } else if (mGroupDay.equals("Tuesday")) {
//            if (mWeekCookingStatus.containsKey("Tuesday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Tuesday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Wednesday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Thursday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Friday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Friday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Saturday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Sunday";
//
//                }
//            } else if (mWeekCookingStatus.containsKey("Monday")) {
//                if (mWeekCookingStatus.containsValue(true)) {
//                    day = "Monday";
//
//                }
//            }
//
//
//            } else if (mGroupDay.equals("Wednesday")) {
//
//                if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                }
//
//            } else if (mGroupDay.equals("Thursday")) {
//
//                if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                }
//
//            } else if (mGroupDay.equals("Friday")) {
//
//                if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                }
//
//            } else if (mGroupDay.equals("Saturday")) {
//                if (mWeekCookingStatus.containsKey("Saturday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Saturday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Sunday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Sunday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Monday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Monday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Tuesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Tuesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Wednesday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Wednesday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Thursday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Thursday";
//
//                    }
//                } else if (mWeekCookingStatus.containsKey("Friday")) {
//                    if (mWeekCookingStatus.containsValue(true)) {
//                        day = "Friday";
//
//                    }
//                }
//
//            }
//


        return day;
    }

    @Override
    public int getMemberCount() {
        return mGroupListNo;
    }
}


