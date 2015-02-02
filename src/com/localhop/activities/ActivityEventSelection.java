package com.localhop.activities;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.localhop.R;

/**
 * Activity for a specific Event selected from the Events List tab.
 * This activity will control the Details, Chat, and Maps tabs for a
 * specific event
 */
public class ActivityEventSelection extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_event_select_main);
        Resources res = getResources();

        // Setup the Back button for user navigation out of the selected event
        ImageButton ibBack = (ImageButton)findViewById(R.id.ibEventBack);
        ibBack.setBackgroundResource(R.drawable.ic_back_arrow_selector);
        ibBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });

        // Create the tabHost and assign TabSpecs for each event select tab
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabDetails = tabHost.newTabSpec("tabDetails");
        TabHost.TabSpec tabChat = tabHost.newTabSpec("tabChat");
        TabHost.TabSpec tabMap = tabHost.newTabSpec("tabMap");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tabDetails.setIndicator("", res.getDrawable(R.drawable.tab_event_details_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_details, tabHost.getTabContentView(), true);
        tabDetails.setContent(R.id.tab_event_select_details);

        tabChat.setIndicator("", res.getDrawable(R.drawable.tab_event_chat_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_chat, tabHost.getTabContentView(), true);
        tabChat.setContent(R.id.tab_event_select_chat);

        tabMap.setIndicator("", res.getDrawable(R.drawable.tab_event_map_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_map, tabHost.getTabContentView(), true);
        tabMap.setContent(R.id.tab_event_select_map);

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tabDetails);
        tabHost.addTab(tabChat);
        tabHost.addTab(tabMap);

        // Set Basic Event UI
        setEventDetails();

    } // end of function onCreate()


    /**
     * Updates the basic Event info such as the Event name and Event start time that display
     * above the custom swipe view
     */
    public void setEventDetails() {

        // UI References
        TextView tvEventName = (TextView)findViewById(R.id.tvEventName);
        TextView tvEventStartTime = (TextView)findViewById(R.id.tvEventTime);
        ImageButton ibEventCalendar = (ImageButton)findViewById(R.id.ibEventCalendar);
        ImageButton ibEventLocation = (ImageButton)findViewById(R.id.ibEventLocation);


        tvEventName.setText("Chipotle");
        tvEventStartTime.setText("5:00 pm");


        // Event Calendar button
        ibEventCalendar.setBackgroundResource(R.drawable.ic_add_box_selector);
        ibEventCalendar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button Click
            }
        });

        // Event Directions button
        ibEventLocation.setBackgroundResource(R.drawable.ic_directions_selector);
        ibEventLocation.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button Click
            }
        });


    } // end of function setEventDetails()

} // end of class ActivityEventSelection
