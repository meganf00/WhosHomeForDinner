package com.mad.whoshomefordinner.fragments.home.view;

import android.view.View;

import java.util.List;

/**
 * Created by Megan on 29/5/18.
 */

public interface HomeFragmentView {
    void setUpUser();
    void showProgressDialog();
    void hideProgressDialog();
    void setUpFragment();
    void setUpGroups();
    void getGroups();

    void updateRow();
    void initiateView();

    void getAllocatedCooks();
    void updateHomeStatus(View view, int position);
}
