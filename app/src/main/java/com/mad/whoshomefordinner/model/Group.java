package com.mad.whoshomefordinner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Megan on 23/5/18.
 */

public class Group {

    private String mId;
    private String mName;
    private List<String> mGroupMembers = new ArrayList<>();
    private String mDay;
    private String mAllocatedCook;
    private String mMeal;
    private String mDeadline;

    public Group(String id, String name, List<String> groupMembers, String day,
                 String allocatedCook, String meal, String deadline) {
        mId = id;
        mName = name;
        mGroupMembers = groupMembers;
        mDay = day;
        mAllocatedCook = allocatedCook;
        mMeal = meal;
        mDeadline = deadline;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getGroupMembers() {
        return mGroupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        mGroupMembers = groupMembers;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getAllocatedCook() {
        return mAllocatedCook;
    }

    public void setAllocatedCook(String allocatedCook) {
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
