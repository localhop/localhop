package com.localhop;

/* Native Java libs ---------------------------------------------------------*/
import java.util.Arrays;                              // for asList

/* Native android libs ------------------------------------------------------*/
import android.app.Activity;
import android.app.TabActivity;
import android.app.Dialog;                            // for Dialog
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/* Google libs      ---------------------------------------------------------*/
import com.google.android.gms.common.ConnectionResult;// for connection stats
import com.google.android.gms.common.api.*;           // for GoogleApiClient
import com.google.android.gms.drive.*;                // for testing. Delete
import com.google.android.gms.common.GooglePlayServicesUtil; // for APK check


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

        /////////////////////////////////////////////////////////////
        // Create the main TabHost that will contain app-navigation
        ////////////////////////////////////////////////////////////
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
        ////////////////////////////////////////////////////////////

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
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(state, this, 1);
            // for now the dialog just won't show
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

} // end of class MainActivity
