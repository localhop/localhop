package com.localhop.swipe.eventlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.localhop.activities.event.ActivityEventSelection;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import com.localhop.objects.Event;
import com.localhop.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class EventListSwipe extends Fragment {

    private int mCurrentPage;           //< The current view of the swipe tabs
    private ArrayList<Event> mEvents;   //< All events associated to a user (Past, Today, and Future)
    private View mEventListView;        //< The event list view for the swipe view

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the arguments to the Bundle object
        Bundle data = getArguments();

        // Getting integer data of the key current_page from the bundle
        mCurrentPage = data.getInt("current_page", 0);

    } // end of function onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create the view for the event list items to be returned
        mEventListView = inflater.inflate(R.layout.tab_event_list_view, container, false);

        // Get all events for a user and split them up based on type of event?
        getAllUserEvents();

        return mEventListView;
    } // end of function onCreateView()

    /**
     * Retrieves all events associated to a user from the DB
     */
    private void getAllUserEvents() {

            new HttpServerRequest<Activity, ArrayList<Event>>(getActivity(), HttpRequest.GET) {

                @Override protected ArrayList<Event> onResponse(final String response) {
                    try {
                        final JSONArray arr = new JSONObject(response).getJSONArray("json").getJSONArray(0);

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
                    layoutFragment();
                }

            }.execute("http://24.124.60.119/get/user/events/2");

    } // end of function getAllUserEvents()


    /**
     * Layouts the view for the fragment
     */
    public void layoutFragment() {

        if( mEvents != null )
        {
            // Split up the events based on Event Type (i.e. Past, Today, or Future)
            Event.EventType type = Event.EventType.Today;
            ArrayList<Event> events = new ArrayList<Event>();
            Date currentDate = new Date();
            Event tempEvent;
            switch (mCurrentPage) {
                case 0:
                    // Past Events
                    type = Event.EventType.Past;

                    for(int i = 0; i < mEvents.size(); i++)
                    {
                        tempEvent = mEvents.get(i);
                        if( tempEvent.getStartDateTime().getDate() < currentDate.getDate())
                        {
                            events.add(tempEvent);
                        }
                    }
                    break;
                case 1:
                    // Today Events
                    for(int i = 0; i < mEvents.size(); i++)
                    {
                        tempEvent = mEvents.get(i);
                        if( tempEvent.getStartDateTime().getDate() == currentDate.getDate())
                        {
                            events.add(tempEvent);
                        }
                    }
                    break;
                case 2:
                    // Future Events
                    type = Event.EventType.Future;
                    for(int i = 0; i < mEvents.size(); i++)
                    {
                        tempEvent = mEvents.get(i);
                        if( tempEvent.getStartDateTime().getDate() > currentDate.getDate())
                        {
                            events.add(tempEvent);
                        }
                    }
                    break;
            }

            // Pass context and data to the custom adapter
            final AdapterEventList eventListAdapter = new AdapterEventList(mEventListView.getContext(),
                    events);

            // Get ListView from tab_event_list_swipe.xml
            ListView lvEvents = (ListView) mEventListView.findViewById(R.id.lvEvents);

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
        }
    } // end of function layoutFragment()

    private void setEvents(final ArrayList<Event> events) {
        mEvents = events;
    }

} // end of class EventListSwipe
