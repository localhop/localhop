package com.localhop.activities.event;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import com.localhop.swipe.createevent.CreateEventSwipeAdapter;
import com.localhop.R;
import com.localhop.swipe.viewpagersupport.ZoomOutPageTransformer;

/**
 * Activity for the Create Event tab in the main navigation tabs.
 */
public class ActivityTabCreateEvent extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_create_event_swipe);

        // Set up the custom Swipe View for Create Event Details and Invite Pages
        ViewPager pager = (ViewPager) findViewById(R.id.pCreateEventDetailInvite);
        FragmentManager fm = getSupportFragmentManager();
        CreateEventSwipeAdapter pagerAdapter = new CreateEventSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());

    } // end of function onCreate()

} // end of class ActivityTabCreateEvent
