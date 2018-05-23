package com.mad.whoshomefordinner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Megan on 23/5/18.
 */

public class User {
    private int mId;
    private String mName;
    private String mEmail;
    private LinkedList<String> mGroups;

    public User(int id, String name, String email, LinkedList<String> groups) {

        mId = id;
        mName = name;
        mEmail = email;
        mGroups = groups;
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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public LinkedList<String> getGroups() {
        return mGroups;
    }

    public void setGroups(LinkedList<String> groups) {
        mGroups = groups;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", mId);
        result.put("name", mName);
        result.put("email", mEmail);
        result.put("groups", mGroups);

        return result;
    }

}
