package com.localhop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.localhop.fragment.EventSwipeAdapter;

/**
 * Activity for a specific Event selected from the Events List tab.
 * This activity will control the Details, Chat, and Maps tabs for a
 * specific event
 */
public class ActivityEventSelection extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_event_swipe);

        // Set up the custom Swipe View for Details, Chat and Map for an event
        ViewPager pager = (ViewPager) findViewById(R.id.pEvent);
        FragmentManager fm = getSupportFragmentManager();
        EventSwipeAdapter pagerAdapter = new EventSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);

        // Set Event Name, Start Time, and Back button UI
        TextView tvEventName = (TextView)findViewById(R.id.tvEventName);
        TextView tvEventStartTime = (TextView)findViewById(R.id.tvEventTime);

        tvEventName.setText("Chipotle");
        tvEventStartTime.setText("5:00 pm");

    } // end of function onCreate()

} // end of class ActivityEventSelection
