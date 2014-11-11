package com.localhop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class MainActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /////////////////////////////////////////////////////////////
        // Create the main TabHost that will contain app-navigation
        ////////////////////////////////////////////////////////////
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabSpec tabCreateEvent = tabHost.newTabSpec("tabCreateEvent");
        TabSpec tabEventList = tabHost.newTabSpec("tabEventList");
        TabSpec tabUserManage = tabHost.newTabSpec("tabUserManage");
        TabSpec tabMap = tabHost.newTabSpec("tabMap");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tabCreateEvent.setIndicator("Create", getResources().getDrawable(R.drawable.tab_create_event_selector));
        tabCreateEvent.setContent(new Intent(this,TabCreateEventActivity.class));

        tabEventList.setIndicator("Events", getResources().getDrawable(R.drawable.tab_event_list_selector));
        tabEventList.setContent(new Intent(this,TabEventListActivity.class));

        tabUserManage.setIndicator("User", getResources().getDrawable(R.drawable.tab_user_selector));
        tabUserManage.setContent(new Intent(this,TabUserManageActivity.class));

        tabMap.setIndicator("Map", getResources().getDrawable(R.drawable.tab_map_selector));
        tabMap.setContent(new Intent(this,TabMapActivity.class));

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tabCreateEvent);
        tabHost.addTab(tabEventList);
        tabHost.addTab(tabUserManage);
        tabHost.addTab(tabMap);
        ////////////////////////////////////////////////////////////




    } // end of function onCreate


} // end of class MainActivity
