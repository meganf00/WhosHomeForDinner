package com.mad.whoshomefordinner.fragments.group.view;

import com.mad.whoshomefordinner.base.BaseView;

/**
 * Created by Megan on 30/5/18.
 */

public interface GroupFragmentView extends BaseView {
    void setUpUser();
    void showProgressDialog();
    void hideProgressDialog();
    void setUpFragment();
    void setUpGroups();
    void getGroups();
}
