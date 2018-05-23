package com.mad.whoshomefordinner;

import java.util.LinkedList;

/**
 * Created by Megan on 23/5/18.
 */

public class Group {

    private int mId;
    private String mName;
    private LinkedList<Integer> mGroupMembers;
    private LinkedList<String> mWeekDays;
    private String mDay;

    public Group(int id, String name, LinkedList<Integer> groupMembers, LinkedList<String> weekDays, String day) {
        mId = id;
        mName = name;
        mGroupMembers = groupMembers;
        mWeekDays = weekDays;
        mDay = day;
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

    public LinkedList<Integer> getGroupMembers() {
        return mGroupMembers;
    }

    public void setGroupMembers(LinkedList<Integer> groupMembers) {
        mGroupMembers = groupMembers;
    }

    public LinkedList<String> getWeekDays() {
        return mWeekDays;
    }

    public void setWeekDays(LinkedList<String> weekDays) {
        mWeekDays = weekDays;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }
}
