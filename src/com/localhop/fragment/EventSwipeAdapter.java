package com.localhop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.localhop.R;
import com.viewpagerindicator.IconPagerAdapter;


/**
 * Adapter for the custom Swipe View for a specif event.  The user will be given the ability
 * to swipe between an Event's details, chat, and map pages.
 */
public class EventSwipeAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

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
     * Returns the icon id of the current tab within the Swipe View
     * @param position
     * @return
     */
    @Override public int getIconResId(int position) {

        int layout = 0;

        switch (position) {

            case 0:
                layout = R.drawable.tab_event_details_selector;
                break;
            case 1:
                layout = R.drawable.tab_event_chat_selector;
                break;
            case 2:
                layout = R.drawable.tab_event_map_selector;
                break;
        }

        return layout;
    } // end of function getIconResId()

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
                title = "DETAILS";
                break;
            case 1:
                title = "CHAT";
                break;
            case 2:
                title = "MAP";
                break;
        }

        return title;

    } // end of function getPageTitle()

} // end of class EventSwipeAdapter
