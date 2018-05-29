package com.mad.whoshomefordinner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Megan on 23/5/18.
 */

public class Group {

    private int mId;
    private String mName;
    private List<User> mGroupMembers = new ArrayList<>();
    private List<String> mWeekDays = new ArrayList<>();
    private String mDay;
    private User mAllocatedCook;
    private String mMeal;
    private String mDeadline;

    public Group(int id, String name, List<User> groupMembers, List<String> weekDays, String day,
                 User allocatedCook, String meal, String deadline) {
        mId = id;
        mName = name;
        mGroupMembers = groupMembers;
        mWeekDays = weekDays;
        mDay = day;
        mAllocatedCook = allocatedCook;
        mMeal = meal;
        mDeadline = deadline;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<User> getGroupMembers() {
        return mGroupMembers;
    }

    public void setGroupMembers(List<User> groupMembers) {
        mGroupMembers = groupMembers;
    }

    public List<String> getWeekDays() {
        return mWeekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        mWeekDays = weekDays;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public User getAllocatedCook() {
        return mAllocatedCook;
    }

    public void setAllocatedCook(User allocatedCook) {
        mAllocatedCook = allocatedCook;
    }

    public String getMeal() {
        return mMeal;
    }

    public void setMeal(String meal) {
        mMeal = meal;
    }

    public String getDeadline() {
        return mDeadline;
    }

    public void setDeadline(String deadline) {
        mDeadline = deadline;
    }
}
