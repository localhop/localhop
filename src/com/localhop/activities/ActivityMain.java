package com.localhop.activities;

/* Native Java libs ---------------------------------------------------------*/

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.localhop.R;
import com.localhop.activities.account.ActivityAccountLogin;
import com.localhop.activities.event.ActivityTabCreateEvent;
import com.localhop.activities.event.ActivityTabEventList;
import com.localhop.prefs.Prefs;
import com.localhop.prefs.PrefsActivity;
import com.localhop.utils.ActivityUtils;


public class ActivityMain extends TabActivity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Prefs.initPrefs(this);
        createMainNavigationTabs(); // Creates the main navigation TabHost

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.localhop_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                return ActivityUtils.startActivityFromClass(this, ActivityAccountLogin.class);
            case R.id.settings:
                return ActivityUtils.startActivityFromClass(this, PrefsActivity.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Creates the main TabHost that will contain app-navigation for Creating Events,
     * Listing Events, User Management, and the Main Map.
     */
    private void createMainNavigationTabs() {

        // Create the tabHost and assign TabSpecs for each main tab
        TabHost tabHost = ActivityUtils.findViewById(this, android.R.id.tabhost);
        TabSpec tabCreateEvent = tabHost.newTabSpec("tabCreateEvent");
        TabSpec tabEventList = tabHost.newTabSpec("tabEventList");
        TabSpec tabUserManage = tabHost.newTabSpec("tabUserManage");
        TabSpec tabMap = tabHost.newTabSpec("tabMap");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tabCreateEvent.setIndicator("", getResources().getDrawable(R.drawable.tab_create_event_selector));
        tabCreateEvent.setContent(new Intent(this, ActivityTabCreateEvent.class));
        tabEventList.setIndicator("", getResources().getDrawable(R.drawable.tab_event_list_selector));
        tabEventList.setContent(new Intent(this, ActivityTabEventList.class));
        tabUserManage.setIndicator("", getResources().getDrawable(R.drawable.tab_user_selector));
        tabUserManage.setContent(new Intent(this, ActivityTabUserManage.class));
        tabMap.setIndicator("", getResources().getDrawable(R.drawable.tab_map_selector));
        tabMap.setContent(new Intent(this, ActivityTabMap.class));

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tabCreateEvent);
        tabHost.addTab(tabEventList);
        tabHost.addTab(tabUserManage);
        tabHost.addTab(tabMap);
    } // end of function createMainNavigationTabs()

} // end of class MainActivity
