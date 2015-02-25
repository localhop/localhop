package com.localhop.swipe.createevent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter for the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class CreateEventSwipeAdapter extends FragmentPagerAdapter{

    final int TAB_COUNT = 2;

    /**
     * Constructor
     * @param fm
     */
    public CreateEventSwipeAdapter(FragmentManager fm) {
        super(fm);
    } // end of Constructor

    /**
     * Invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int position) {

        EventListSwipe myFragment = new EventListSwipe();
        Bundle data = new Bundle();
        data.putInt("current_page", position);
        myFragment.setArguments(data);
        return myFragment;
    } // end of function getItem()


    /**
     *  Returns the number of tabs for the Swipe View
     */
    @Override
    public int getCount() {
        return TAB_COUNT;
    } // end of function getCount()

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position) {

            case 0:
                title = "Details";
                break;
            case 1:
                title = "Invite";
                break;
        }

        return title;

    } // end of function getPageTitle()

} // end of class EventListSwipeAdapter