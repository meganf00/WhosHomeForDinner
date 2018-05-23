package com.mad.whoshomefordinner;

import java.util.LinkedList;

/**
 * Created by Megan on 23/5/18.
 */

public class Day {
    private int mAllocatedCook;
    private int mDeadline;
    private String mMeal;
    private LinkedList<Integer> mMembersHome;

    public Day(int allocatedCook, int deadline, String meal, LinkedList<Integer> membersHome) {
        mAllocatedCook = allocatedCook;
        mDeadline = deadline;
        mMeal = meal;
        mMembersHome = membersHome;
    }

    public int getAllocatedCook() {
        return mAllocatedCook;
    }

    public void setAllocatedCook(int allocatedCook) {
        mAllocatedCook = allocatedCook;
    }

    public int getDeadline() {
        return mDeadline;
    }

    public void setDeadline(int deadline) {
        mDeadline = deadline;
    }

    public String getMeal() {
        return mMeal;
    }

    public void setMeal(String meal) {
        mMeal = meal;
    }

    public LinkedList<Integer> getMembersHome() {
        return mMembersHome;
    }

    public void setMembersHome(LinkedList<Integer> membersHome) {
        mMembersHome = membersHome;
    }
}
