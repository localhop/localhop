package com.localhop.activities;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
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

        setContentView(R.layout.tab_event_select_swipe);
        Resources res = getResources();

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

        // Set Event Name, Start Time, and Back button UI
        TextView tvEventName = (TextView)findViewById(R.id.tvEventName);
        TextView tvEventStartTime = (TextView)findViewById(R.id.tvEventTime);

        tvEventName.setText("Chipotle");
        tvEventStartTime.setText("5:00 pm");

    } // end of function setEventDetails()

} // end of class ActivityEventSelection
