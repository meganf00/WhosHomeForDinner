package com.mad.whoshomefordinner.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Megan on 20/5/18.
 *
 * BaseActivity is the base java class implemented by
 * each activity in this application, used to share common methods
 * between activity classes
 */

public class BaseActivity extends AppCompatActivity {
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
   }
}
