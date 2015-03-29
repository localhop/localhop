package com.localhop.activities.event;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
        final ViewPager pager = (ViewPager) findViewById(R.id.pCreateEventDetailInvite);
        FragmentManager fm = getSupportFragmentManager();
        CreateEventSwipeAdapter pagerAdapter = new CreateEventSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        pager.setOffscreenPageLimit(2);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());

        // On the invite page, we need to grab the data from the Details page.
        // Unfortunately, we cannot do this from within CreateEventSwipe.java because
        // the fragment gets blown away and the UI components are null.
        // However, this seems to work fine for the time being.
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not Used
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1)
                {
                    ImageButton createEventImageButton = (ImageButton)pager.findViewById(R.id.ib_create_event);
                    createEventImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText eventNameEditText = (EditText)pager.findViewById(R.id.et_create_event_name);
                            // TODO: Get other UI Components from Details page

                            if(!eventNameEditText.getText().toString().isEmpty())
                            {
                               // TODO: Query the DB (This query is already in CreateEventSwipe.java)
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), R.string.missing_event_name_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not Used
            }
        });

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
