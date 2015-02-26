package com.localhop.swipe.createevent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter for the custom Swipe View on the Create Event tab.
 * The user will be given the ability to swipe between the Details
 * and Invite page while creating an Event.
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

        Bundle data = new Bundle();
        data.putInt("current_page", position);

        CreateEventSwipe createEventSwipe = new CreateEventSwipe();
        createEventSwipe.setArguments(data);
        return createEventSwipe;
    } // end of function getItem()


    /**d
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
                title = "Details";
                break;
            case 1:
                title = "Invite";
                break;
        }

        return title;

    } // end of function getPageTitle()

} // end of class CreateEventSwipeAdapter