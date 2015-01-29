package com.localhop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.localhop.fragment.EventSwipeAdapter;
import com.viewpagersupport.TabPageIndicator;
import com.viewpagersupport.UnderlinePageIndicator;

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

        // Set up the icons for the custom swipe view
        TabPageIndicator tabs = (TabPageIndicator)findViewById(R.id.tpiEvent);
        tabs.setViewPager(pager);

        // Set up the underline effect for the tabs of the custom swipe view
        UnderlinePageIndicator underline = (UnderlinePageIndicator)findViewById(R.id.upiEvent);
        underline.setViewPager(pager);

        // Set Basic Event UI
        setEventDetails();

    } // end of function onCreate()


    /**
     * Updates the basic Event info such as the Event name and Event start time that display
     * above the custom swipe view
     */
    public void setEventDetails() {

        // Set Event Name, Start Time, and Back button UI
        TextView tvEventName = (TextView)findViewById(R.id.tvEventName);
        TextView tvEventStartTime = (TextView)findViewById(R.id.tvEventTime);

        tvEventName.setText("Chipotle");
        tvEventStartTime.setText("5:00 pm");

    } // end of function setEventDetails()

} // end of class ActivityEventSelection
