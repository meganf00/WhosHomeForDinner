package com.mad.whoshomefordinner.fragments.schedule;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.whoshomefordinner.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * Java class that corresponds to the fragment layout ScheduleFragment and
 * generates the fragment
 */
public class ScheduleFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_schedule));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

}
