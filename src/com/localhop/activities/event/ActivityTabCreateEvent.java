package com.localhop.activities.event;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.localhop.swipe.createevent.CreateEventSwipeAdapter;
import com.localhop.R;
import com.localhop.swipe.viewpagersupport.ZoomOutPageTransformer;
import com.localhop.utils.ViewUtils;

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

        // Setup the back button to get out of the create new event page
        setupBackButton();


    } // end of function onCreate()

    /**
     * Setup the Back button that allows the user to navigate back from the create event page
     */
    private void setupBackButton() {
        ImageButton backImageButton = (ImageButton) findViewById(R.id.ib_create_event_back_button);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Once the favorites/recent events page is complete, set this to finish
            }
        });
    } // end of function setupBackButton()

} // end of class ActivityTabCreateEvent
