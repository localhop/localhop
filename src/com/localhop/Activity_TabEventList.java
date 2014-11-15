package com.localhop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import com.localhop.fragment.EventListSwipeAdapter;

/**
 * Activity for the Event List tab in the main navigation tabs.
 */
public class Activity_TabEventList extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_events_list_swipe);

        setupSwipeView(); // Set up the custom Swipe View for Past, Today, and Future events

    } // end of function onCreate()

    /**
     * Sets up the custom Swipe View for displaying Past, Today, and Future events
     */
    void setupSwipeView() {

        ViewPager pager = (ViewPager) findViewById(R.id.pEventList);
        FragmentManager fm = getSupportFragmentManager();
        EventListSwipeAdapter pagerAdapter = new EventListSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);

    } // end of function setupSwipeView()

} // end of class TabEventListActivity
