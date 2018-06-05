package com.mad.whoshomefordinner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Megan on 23/5/18.
 *
 * Class user is created to make objects based on parsed data
 */

public class User implements Parcelable{
    private String mId;
    private String mName;
    private String mEmail;
    private List<String> mGroups = new ArrayList<>();

    public User(String id, String name, String email, List<String> groups) {

        mId = id;
        mName = name;
        mEmail = email;
        mGroups = groups;
    }


    protected User(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mEmail = in.readString();
        mGroups = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeStringList(mGroups);
    }
}
