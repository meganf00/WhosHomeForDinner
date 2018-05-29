package com.mad.whoshomefordinner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Megan on 23/5/18.
 */

public class User {
    private String mId;
    private String mName;
    private String mEmail;
    private List<String> mGroups = new ArrayList<>();;

    public User(String id, String name, String email, List<String> groups) {

        mId = id;
        mName = name;
        mEmail = email;
        mGroups = groups;
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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<String> getGroups() {
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
