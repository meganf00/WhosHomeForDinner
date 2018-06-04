package com.mad.whoshomefordinner.groupView.view;

import com.mad.whoshomefordinner.base.BaseView;
import com.mad.whoshomefordinner.model.Group;

/**
 * Created by Megan on 31/5/18.
 */

public interface GroupViewView extends BaseView {
    void setUpUser();
    void showProgressDialog();
    void hideProgressDialog();
    void getGroup();
    void initiateView();
    void setUpGroups();
}
