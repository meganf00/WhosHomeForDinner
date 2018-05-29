package com.mad.whoshomefordinner.main.model.firebase;

import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 27/5/18.
 */

public interface FirebaseInteractor {
    User getUser();
    List<Group> getGroups();
    boolean isSignedIn();
    void logOut();
    void createUser();
    boolean userCreated();
}
