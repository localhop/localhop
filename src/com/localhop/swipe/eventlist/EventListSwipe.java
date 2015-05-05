package com.localhop.swipe.eventlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import com.localhop.activities.event.ActivityEventSelection;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import com.localhop.objects.Event;
import com.localhop.R;
import com.localhop.objects.Friend;
import com.localhop.utils.ViewUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class EventListSwipe extends Fragment {

    private int mCurrentPage;                  //< The current view of the swipe tabs
    private ArrayList<Event> mEvents;          //< All events associated to a user (Past, Today, and Future)
    private View mEventListView;               //< The event list view for the swipe view
    private SwipeRefreshLayout mRefreshLayout; // Swipe to refresh the events, ooooh, aaaah...

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the arguments to the Bundle object
        Bundle data = getArguments();

        // Getting integer data of the key current_page from the bundle
        mCurrentPage = data.getInt("current_page", 0);

    } // end of function onCreate()

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create the view for the event list items to be returned
        mEventListView = inflater.inflate(R.layout.tab_event_list_view, container, false);
        mRefreshLayout = ViewUtils.findViewById(mEventListView, R.id.srl_events);

        // Get all events for a user and split them up based on type of event?
        getAllUserEvents();

        return mEventListView;
    } // end of function onCreateView()

    /**
     * Retrieves all events associated to a user from the DB
     */
    private void getAllUserEvents() {

            new HttpServerRequest<Activity, ArrayList<Event>>(getActivity(), HttpRequest.GET, null) {

                @Override protected ArrayList<Event> onResponse(final String response) {
                    try {
                        final JSONArray arr = new JSONObject(response).getJSONArray("text");
                        ArrayList<Event> events = new ArrayList<Event>();
                        for (int i = 0; i < arr.length(); ++i) {
                            final JSONObject obj = arr.getJSONObject(i);
                            events.add(Event.fromJSON(obj));
                        }

                        return events;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null; // TODO: null voodoo
                    }
                }

                @Override protected void onPostExecute(ArrayList<Event> events) {
                    super.onPostExecute(events);
                    setEvents(events);
                    setEventAttendees();
                    layoutFragment();
                    mRefreshLayout.setRefreshing(false);
                }

                @Override protected void onCancelled() {
                    mRefreshLayout.setRefreshing(false);
                }

            }.execute("http://24.124.60.119/user/events/2");

    } // end of function getAllUserEvents()

    /**
     * Returns all people invited to an event
     */
    public void getEventAttendees(int eventID, final int eventIndex) {

        new HttpServerRequest<Activity, ArrayList<Friend>>(getActivity(), HttpRequest.GET, null) {

            @Override protected ArrayList<Friend> onResponse(final String response) {
                try {
                    final JSONArray arr = new JSONObject(response).getJSONArray("text");
                    ArrayList<Friend> attendeeList = new ArrayList<Friend>();
                    for (int i = 0; i < arr.length(); ++i) {
                        final JSONObject obj = arr.getJSONObject(i);
                        attendeeList.add(Friend.fromJSON(obj, ""));
                    }

                    return attendeeList;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null; // TODO: null voodoo
                }
            }

            @Override protected void onPostExecute(ArrayList<Friend> attendeeList) {
                super.onPostExecute(attendeeList);
                mEvents.get(eventIndex).setAttendees(attendeeList);
            }

            @Override protected void onCancelled() {
            }
        }.execute("http://24.124.60.119/event/users/" + eventID); // all attendees will be returned for event id
    } // end of function getEventAttendees()

    public void setEventAttendees(){

        // Get the attendees for each event
        for(int i = 0; i < mEvents.size(); i++){
            getEventAttendees(mEvents.get(i).getEventID(), i);
        }

    }
    /**
     * Layouts the view for the fragment
     */
    public void layoutFragment() {

        if( mEvents != null )
        {
            // Split up the events based on Event Type (i.e. Past, Today, or Future)
            ArrayList<Event> pastEvents = new ArrayList<Event>();
            ArrayList<Event> todayEvents = new ArrayList<Event>();
            ArrayList<Event> futureEvents = new ArrayList<Event>();
            Event tempEvent;
            for(int i = 0; i < mEvents.size(); i++)
            {
                tempEvent = mEvents.get(i);
                if( tempEvent.getType() == Event.EventType.Past )
                {
                    pastEvents.add(tempEvent);
                }
                else if( tempEvent.getType() == Event.EventType.Today )
                {
                    todayEvents.add(tempEvent);
                }
                else if( tempEvent.getType() == Event.EventType.Future )
                {
                    futureEvents.add(tempEvent);
                }
            }

            switch (mCurrentPage) {
                case 0:
                    // Past Events
                    // Sort the events in DESC order (latest event first)
                    Collections.reverse(pastEvents);
                    setupEventSelection(pastEvents);
                    break;
                case 1:
                    // Today Events
                    setupEventSelection(todayEvents);
                    break;
                case 2:
                    // Future Events
                    setupEventSelection(futureEvents);
                    break;
            }
        }
    } // end of function layoutFragment()


    private void setEvents(final ArrayList<Event> events) {
        mEvents = events;
    }

    /**
     * Sets up the Search View and handles searches for specific events
     */
    private void searchEvents(){

        SearchView sv = ViewUtils.findViewById(mEventListView, R.id.svEventList);
    }

    /**
     * Sets up and handles event selections
     * @param events
     */
    private void setupEventSelection(ArrayList<Event> events){

        // Pass context and data to the custom adapter
        final AdapterEventList eventListAdapter = new AdapterEventList(mEventListView.getContext(),
                events);

        // Get ListView from tab_event_list_swipe.xml
        ListView lvEvents = ViewUtils.findViewById(mEventListView, R.id.lvEvents);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                getAllUserEvents();
            }
        });

        // Set the List Adapter
        lvEvents.setAdapter(eventListAdapter);

        // Set the ListView Item Listener
        lvEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Prepare to start ActivityEventSelection
                Intent eventSelection = new Intent(view.getContext(), ActivityEventSelection.class);
                // Pass the selected event object to the ActivityEventSelection.
                eventSelection.putExtra("event", eventListAdapter.getItem(position));
                startActivity(eventSelection);
            }
        });
    } // end of class setupEventSelection

} // end of class EventListSwipe
