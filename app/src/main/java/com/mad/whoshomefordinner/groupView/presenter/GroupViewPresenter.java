package com.mad.whoshomefordinner.groupView.presenter;

import com.mad.whoshomefordinner.base.BasePresenter;
import com.mad.whoshomefordinner.groupView.view.GroupViewView;
import com.mad.whoshomefordinner.model.User;

/**
 * Created by Megan on 31/5/18.
 */

public interface GroupViewPresenter extends BasePresenter<GroupViewView> {
    User getCurrentUser();
    void onStart();
    void onStop();
    void userCreated();
    void setUpInteractor();
    GroupViewPresenterImpl getPresenter();
    void connectWithInteractor();
    void setUpUser();

    void setUpAllocatedCook();

    boolean isUserAllocatedCook();

    void allocatedCookNameGenerated();

    String getAllocatedCook();

    boolean userIsHome();

    void setNextDate();

    void cookingDaysGenerated();

    String getNextCookingDay();

    int getNoMembers();
}
