package com.localhop.activities.event;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;

import com.localhop.R;
import com.localhop.swipe.eventlist.EventListSwipeAdapter;
import com.localhop.swipe.viewpagersupport.ZoomOutPageTransformer;
import com.localhop.utils.ActivityUtils;

/**
 * Activity for the Event List tab in the main navigation tabs.
 */
public class ActivityTabEventList extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_event_list_swipe);

        // Set up the custom Swipe View for Past, Today, and Future events
        ViewPager pager = ActivityUtils.findViewById(this, R.id.pEventList);
        FragmentManager fm = getSupportFragmentManager();
        EventListSwipeAdapter pagerAdapter = new EventListSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());


        // Set up the Search bar that allows events to be searched for
        SearchView search = ActivityUtils.findViewById(this, R.id.svEventList);
        search.setIconifiedByDefault(false);
        search.setFocusable(false);

    } // end of function onCreate()

} // end of class ActivityTabEventList
