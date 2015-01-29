package com.localhop.swipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localhop.R;

/**
 * Controls the custom Swipe View on a specific Events page.  The user will be given the ability
 * to swipe between a specific event's Details, Chat, and Map pages.
 */
public class EventSelectSwipe extends Fragment{

    private int mCurrentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the arguments to the Bundle object
        Bundle data = getArguments();

        // Getting integer data of the key current_page from the bundle
        mCurrentPage = data.getInt("current_page", 0);
    } // end of function onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Determine which layout to load based on the swipe tab position
        View eventView = null;
        switch (mCurrentPage) {
            case 0:
                // Details
                eventView = inflater.inflate(R.layout.tab_event_select_details, container, false);
                setDetailsPage(eventView);
                break;
            case 1:
                // Chat
                eventView = inflater.inflate(R.layout.tab_event_select_chat, container, false);
                break;
            case 2:
                // Map
                eventView = inflater.inflate(R.layout.tab_event_select_map, container, false);
                break;
        }

        return eventView;
    } // end of function onCreateView()


    /**
     * Sets the UI for a specific Event's Details page
     * @param eventView
     */
    private void setDetailsPage(View eventView) {

        TextView tvEventStartTime = (TextView)eventView.findViewById(R.id.tvEventStartTime);

        tvEventStartTime.setText("5:00-6:00 pm");

    } // end of function setDetailsPage()

    private void setChatPage() {
    }

    private void setMapPage() {
    }

} // end of class EventSwipe

