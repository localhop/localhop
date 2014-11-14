package com.localhop;

/* Native Java libs ---------------------------------------------------------*/

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.util.Arrays;

/* Native android libs ------------------------------------------------------*/
/* Google libs      ---------------------------------------------------------*/


public class Activity_Main extends TabActivity
    implements GoogleApiClient.ConnectionCallbacks,
               GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        createMainNavigationTabs(); // Creates the main navigation TabHost

        /**
         * set up GoogleApiClient instance.  This is what we use to connect our
         * app to google play services and access APIs.
         */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)              // !! delete
                .addScope(Drive.SCOPE_FILE)     // !! delete
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        /**
         * Here we are checking to see if the google play services api is up to
         * data on the user's device. If only certain features of our app will
         * be utilizing google player services then we can move this set of
         * calls to a different location so that they don't fire every time the
         * application resumes. For now, we will just leave these checks here.
         */
        int[] error_state = {ConnectionResult.SERVICE_MISSING,
                             ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED,
                             ConnectionResult.SERVICE_DISABLED};

        /**
         * Get the current APK status. If SUCCESS is not returned then the user
         * needs to download an updated version of the google play service.
         */
        int state = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (state == ConnectionResult.SUCCESS) {
            return;
        } else if (Arrays.asList(error_state).contains(state)) {
            // for now this dialog just won't show.  We need to switch to using
            // Fragments instead of TabActivity.
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(state, this, 1);
            //
        }

    }


    @Override
    public void onConnected(Bundle connectionHint) {
        // connected to google play services
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // connection has been interrupted. Disable any UI components that
        // depend on google apis until onConnected() is called.
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // handle errors associated with connecting to google apis.
    }


    /**
     * Creates the main TabHost that will contain app-navigation for Creating Events,
     * Listing Events, User Management, and the Main Map.
     */
    private void createMainNavigationTabs() {

        // Create the tabHost and assign TabSpecs for each main tab
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        TabSpec tabCreateEvent = tabHost.newTabSpec("tabCreateEvent");
        TabSpec tabEventList = tabHost.newTabSpec("tabEventList");
        TabSpec tabUserManage = tabHost.newTabSpec("tabUserManage");
        TabSpec tabMap = tabHost.newTabSpec("tabMap");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tabCreateEvent.setIndicator("", getResources().getDrawable(R.drawable.tab_create_event_selector));
        tabCreateEvent.setContent(new Intent(this, Activity_TabCreateEvent.class));
        tabEventList.setIndicator("", getResources().getDrawable(R.drawable.tab_event_list_selector));
        tabEventList.setContent(new Intent(this, Activity_TabEventList.class));
        tabUserManage.setIndicator("", getResources().getDrawable(R.drawable.tab_user_selector));
        tabUserManage.setContent(new Intent(this, Activity_TabUserManage.class));
        tabMap.setIndicator("", getResources().getDrawable(R.drawable.tab_map_selector));
        tabMap.setContent(new Intent(this, Activity_TabMap.class));

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tabCreateEvent);
        tabHost.addTab(tabEventList);
        tabHost.addTab(tabUserManage);
        tabHost.addTab(tabMap);
    } // end of function createMainNavigationTabs()

} // end of class MainActivity
