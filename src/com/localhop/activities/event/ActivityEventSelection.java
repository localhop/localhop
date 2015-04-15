package com.localhop.activities.event;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.localhop.R;
import com.localhop.network.GPSTracker;
import com.localhop.objects.DateTime;
import com.localhop.objects.Event;
import com.localhop.utils.ActivityUtils;

import java.util.Date;

/**
 * Activity for a specific Event selected from the Events List tab.
 * This activity will control the Details, Chat, and Maps tabs for a
 * specific event
 */
public class ActivityEventSelection extends TabActivity {

    private Event event;
    private GoogleMap mMap;
    private DateTime mDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_event_select_main);

        // Get the selected event
        Intent eventListIntent = getIntent();
        this.event = (Event)eventListIntent.getSerializableExtra("event");

        // Set up the main UI
        setupUI();

        // Set the UI and data for each tab
        setEventDetails();
        // TODO: setEventChat();
        setEventMap();

    } // end of function onCreate()


    /**
     * Sets the Details tab for a selected event.
     */
    public void setEventDetails() {

        // UI Components
        TextView tvEventName          = ActivityUtils.findViewById(this, R.id.tvEventName);
        TextView tvEventStartDate     = ActivityUtils.findViewById(this, R.id.tvEventStartDate);
        TextView tvEventStartTime     = ActivityUtils.findViewById(this, R.id.tvEventStartTime);
        EditText etEventDetails       = ActivityUtils.findViewById(this, R.id.etEventDetails);
        EditText etEventLocation      = ActivityUtils.findViewById(this, R.id.etEventLocation);
        ImageButton ibEventCalendar   = ActivityUtils.findViewById(this, R.id.ibEventCalendar);
        ImageButton ibEventLocation   = ActivityUtils.findViewById(this, R.id.ibEventLocation);
        final Button ibEventInvited   = ActivityUtils.findViewById(this, R.id.ibEventInvited);
        final Button ibEventAttending = ActivityUtils.findViewById(this, R.id.ibEventAttending);
        RelativeLayout rlEventRSPV    = ActivityUtils.findViewById(this, R.id.rlEventRSPV);
        RelativeLayout rlEventDetails = ActivityUtils.findViewById(this, R.id.rlEventDetails);

        // Add Border lines between sections of the Details Page
        ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class
        Paint paint = rectShapeDrawable.getPaint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        rlEventRSPV.setBackgroundDrawable(rectShapeDrawable);
        rlEventDetails.setBackgroundDrawable(rectShapeDrawable);

        //Temp Data TODO: Delete after DB integration is done
        final CharSequence attendees[] = {"Michelle Perz", "Ryan Scott", "Zach Flies"};
        final CharSequence invited[] = {"Adam Smith", "Kendal Harland"};

        // Add Attending/Invited buttons and setup corresponding dialogs
        // TODO: Modify list items once DB integration is done
        ibEventAttending.setBackgroundResource(R.drawable.ic_notification_circle_black_36dp);
        ibEventAttending.setText("3");
        ibEventAttending.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ibEventAttending.setEnabled( false );
                new AlertDialog.Builder(ActivityEventSelection.this)
                        .setTitle("Attendees")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ibEventAttending.setEnabled(true);
                                dialog.cancel();
                            }
                        })
                        .setItems(attendees, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: What option do we want to give the user when they select a name from the list?
                                ibEventAttending.setEnabled(true);
                            }
                        })
                        .show();

                return false;
            }
        });
        // TODO: Modify list items once DB integration is done
        ibEventInvited.setBackgroundResource(R.drawable.ic_notification_circle_black_36dp);
        ibEventInvited.setText("2");
        ibEventInvited.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ibEventInvited.setEnabled(false);
                new AlertDialog.Builder(ActivityEventSelection.this)
                        .setTitle("Invited")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ibEventInvited.setEnabled(true);
                                dialog.cancel();
                            }
                        })
                        .setItems(invited, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: What option do we want to give the user when they select a name from the list?
                                ibEventInvited.setEnabled(true);
                            }
                        })
                        .show();
                return false;
            }
        });

        // Set Event Name
        tvEventName.setText(event.getName());

        // Set Event Start Date
        DateTime datetime = new DateTime(getApplicationContext(), event.getStartDateTime());
        tvEventStartDate.setText(datetime.getMonthDayYearFormat());

        // Set Event Start and End Time
        String startEventTime = datetime.getTimeFormat();
        datetime.setTime(event.getEndDateTime());
        String endEventTime = datetime.getTimeFormat();
        tvEventStartTime.setText(startEventTime + " - " + endEventTime);

        // Set Event Details
        etEventDetails.setText(event.getDescription());

        // Set Event Location
        etEventLocation.setText(event.getLocation());

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


    /**
     * Sets the Map tab for a selected event.
     */
    public void setEventMap() {

        Switch swBroadcastLocation = ActivityUtils.findViewById(this, R.id.sw_event_select_broadcast_location);

        // Get the User's Last known location
        // TODO: Once Google Places API is linked with the create event pages, we should probably
        // TODO:  use the event lat/long instead of the user's position.

        LatLng locUser = new LatLng(38.957598, -95.252742); // Eaton Hall (:

        LocationManager locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mDateTime = new DateTime(this, new Date());
        String lastKnownUpdate = "";
        if (location != null) {
            locUser = new LatLng(location.getLatitude(), location.getLongitude());
            lastKnownUpdate = mDateTime.getLastKnownUpdateString(new Date(location.getTime()));
        }

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapEventMap)).getMap();
        //final Marker markerUser = mMap.addMarker(new MarkerOptions().position(locUser).title("You"));

        // Set the user's location marker
        final Marker markerUser = mMap.addMarker(new MarkerOptions()
                .position(locUser)
                .title("You")
                .snippet(lastKnownUpdate));
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.ic_launcher))); // This allows you use a custom marker icon
        markerUser.showInfoWindow(); // Show the info window of this marker

        // Center and Zoom and camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locUser, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null); // Zoom level 17

    } // end of function setEventMap()

    /**
     * Link and setup the UI components for this Activity
     */
    public void setupUI() {

        Resources res = getResources();

        // Setup the Back button for user navigation out of the selected event
        ImageButton ibBack = ActivityUtils.findViewById(this, R.id.ibEventBack);
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

        // Set the Tab name and layout
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

    } // end of function setUI()

} // end of class ActivityEventSelection
