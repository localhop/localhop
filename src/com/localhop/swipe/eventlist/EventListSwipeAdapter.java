package com.localhop.swipe.eventlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter for the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class EventListSwipeAdapter extends FragmentPagerAdapter{

    final int TAB_COUNT = 3;

    /**
     * Constructor
     * @param fm
     */
    public EventListSwipeAdapter(FragmentManager fm) {
        super(fm);
    } // end of Constructor

    /**
     * Invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int position) {

        Bundle data = new Bundle();
        data.putInt("current_page", position);

        EventListSwipe eventListSwipe = new EventListSwipe();
        eventListSwipe.setArguments(data);
        return eventListSwipe;
    } // end of function getItem()


    /**
     *  Returns the number of tabs for the Swipe View
     */
    @Override
    public int getCount() {
        return TAB_COUNT;
    } // end of function getCount()


    /**
     * Returns the title of the current tab within the Swipe View
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position) {

            case 0:
                title = "Past";
                break;
            case 1:
                title = "Today";
                break;
            case 2:
                title = "Future";
                break;
        }

        return title;

    } // end of function getPageTitle()

} // end of class EventListSwipeAdapter