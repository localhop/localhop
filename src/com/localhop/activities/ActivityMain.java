package com.localhop.activities;

/* Native Java libs ---------------------------------------------------------*/

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.localhop.R;
import com.localhop.activities.account.ActivityAccountLogin;
import com.localhop.activities.event.ActivityTabCreateEvent;
import com.localhop.activities.event.ActivityTabEventList;
import com.localhop.network.GPSTracker;
import com.localhop.activities.map.MapWibble;
import com.localhop.objects.Event;
import com.localhop.prefs.Prefs;
import com.localhop.prefs.PrefsActivity;
import com.localhop.utils.ActivityUtils;


public class ActivityMain extends TabActivity {

    private int mTabPosition;
    private GPSTracker mGPS;

    private Context context;
    private final int NO_USER = -1;

    private void logout() {
        SharedPreferences preferences = context.getSharedPreferences(
            getString(R.string.localhop_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.user_id_key), NO_USER);
        editor.commit();
    }
    /**
     * Sets the look of the localhop_menu based on the user's authentication status.
     */
    private void setMenuAuthState(Menu menu) {
        SharedPreferences preferences = context.getSharedPreferences(
            getString(R.string.localhop_pref), Context.MODE_PRIVATE);
        boolean userLoggedIn = preferences.getInt(getString(R.string.user_id_key), NO_USER) >= 0;
        menu.findItem(R.id.login).setVisible(!userLoggedIn);
        menu.findItem(R.id.logout).setVisible(userLoggedIn);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();

        // Get the selected event
        Intent eventListIntent = getIntent();
        this.mTabPosition = eventListIntent.getIntExtra("position", 0);

        Prefs.initPrefs(this);
        createMainNavigationTabs(); // Creates the main navigation TabHost

        // TODO: Check if gps location is turned on or requested from the prefs before we activate gps
        mGPS = new GPSTracker(this);
        mGPS.startGPSTracking();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.localhop_menu, menu);
        setMenuAuthState(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        setMenuAuthState(menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                return ActivityUtils.startActivityFromClass(this, ActivityAccountLogin.class);
            case R.id.logout: {
                logout();
                return ActivityUtils.startActivityFromClass(this, ActivityAccountLogin.class);
            }
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

        tabHost.setCurrentTab(mTabPosition);
    } // end of function createMainNavigationTabs()

} // end of class MainActivity
