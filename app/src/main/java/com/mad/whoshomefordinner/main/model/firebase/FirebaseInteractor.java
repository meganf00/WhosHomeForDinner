package com.mad.whoshomefordinner.main.model.firebase;

import com.mad.whoshomefordinner.main.presenter.MainPresenterImpl;
import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 27/5/18.
 */

public interface FirebaseInteractor {
    void getPresenter(MainPresenterImpl mainPresenter);
    User getUser();
    boolean isSignedIn();
    void logOut();
    void createUser();
    boolean userCreated();
}
