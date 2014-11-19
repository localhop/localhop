package com.localhop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Adapter for the custom Swipe View for a specif event.  The user will be given the ability
 * to swipe between an Event's details, chat, and map pages.
 */
public class EventSwipeAdapter extends FragmentPagerAdapter {

    final int TAB_COUNT = 3;

    /**
     * Constructor
     * @param fm
     */
    public EventSwipeAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    } // end of Constructor

    /**
     * Invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("current_page", position);

        EventSwipe eventSwipe = new EventSwipe();
        eventSwipe.setArguments(bundle);
        return eventSwipe;
    } // end of function getItem()

    /**
     * Returns the number of tabs for the Swipe View
     * @return
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
                title = "Details";
                break;
            case 1:
                title = "Chat";
                break;
            case 2:
                title = "Map";
                break;
        }

        return title;

    } // end of function getPageTitle()

} // end of class EventSwipeAdapter
