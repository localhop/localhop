package com.localhop.activities.event;

import android.app.Activity;
import android.os.Bundle;

import com.localhop.R;

public class ActivityTabCreateEvent extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    } // end of function onCreate()
} // end of class TabCreateEventActivity