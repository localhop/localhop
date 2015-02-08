package com.localhop.swipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;
import com.localhop.activities.ActivityEventSelection;
import com.localhop.adapters.AdapterEventList;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import com.localhop.objects.Event;
import com.localhop.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class EventListSwipe extends Fragment {

    private int currentPage;    //< The current view of the swipe tabs
    private Event.EventType mEventType;
    private ArrayList<Event> mEvents;
    private View mEventListItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the arguments to the Bundle object
        Bundle data = getArguments();

        // Getting integer data of the key current_page from the bundle
        currentPage = data.getInt("current_page", 0);

    } // end of function onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create the view for the event list items to be returned
        mEventListItems = inflater.inflate(R.layout.tab_event_list_view, container, false);

        // Determine which layout to load based on the swipe tab position
//        ArrayList<Event> events = null;
//        Event.EventType eventType = null;
        switch (currentPage) {
            case 0:
                // Past Events
//                eventType = Event.EventType.Past;
//                events = generatePastEvents();
                generatePastEvents();
                break;
            case 1:
                // Today Events
//                eventType = Event.EventType.Today;
                generateTodayEvents();
                break;
            case 2:
                // Future Events
//                eventType = Event.EventType.Future;
//                events = generateFutureEvents();
                generateFutureEvents();
                break;
        }

        // Pass context and data to the custom adapter
//        final AdapterEventList adapter = new AdapterEventList(eventListItems.getContext(),
//                events, eventType);
//
//        // Get ListView from tab_event_list_swipe.xml
//        ListView lvEvents = (ListView)eventListItems.findViewById(R.id.lvEvents);
//
//        // Set the List Adapter
//        lvEvents.setAdapter(adapter);
//
//        // Set the ListView Item Listener
//        lvEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Prepare to start ActivityEventSelection
//                Intent eventSelection = new Intent(view.getContext(), ActivityEventSelection.class);
//                // Pass the selected event object to the ActivityEventSelection.
//                eventSelection.putExtra("event", adapter.getItem(position));
//                startActivity(eventSelection);
//            }
//        });

        return mEventListItems;
    } // end of function onCreateView()

    public void layoutFragment() {
        // Pass context and data to the custom adapter
        final AdapterEventList adapter = new AdapterEventList(mEventListItems.getContext(),
                mEvents, mEventType);

        // Get ListView from tab_event_list_swipe.xml
        ListView lvEvents = (ListView)mEventListItems.findViewById(R.id.lvEvents);

        // Set the List Adapter
        lvEvents.setAdapter(adapter);

        // Set the ListView Item Listener
        lvEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Prepare to start ActivityEventSelection
                Intent eventSelection = new Intent(view.getContext(), ActivityEventSelection.class);
                // Pass the selected event object to the ActivityEventSelection.
                eventSelection.putExtra("event", adapter.getItem(position));
                startActivity(eventSelection);
            }
        });
    }

    /**
    * Generates today events list
    * @return
            */
    private void generateTodayEvents(){

        // TODO: DB Call to get event objects

        new HttpServerRequest<Activity, ArrayList<Event>>(getActivity(), HttpRequest.GET) {

            @Override protected ArrayList<Event> onResponse(final String response) {
                try {
                    final JSONArray arr = new JSONObject(response).getJSONArray("json");
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
        }.execute("http://24.124.60.119/getEvents");

//        ArrayList<Event> items = new ArrayList<Event>();
//        items.add(new ListItemEvent(ListItemEvent.EventType.Today, "Chipotle", "",
//                "23rd St. Chipotle", null, null, "Attending: You, Michelle, Ryan", 0, 0, 0));
//
//        items.add(new ListItemEvent(ListItemEvent.EventType.Today, "Senior Design Meetup", "",
//                "Spahr Library", null, null, "Attending: You, Adam, Kendal", 0, 0, 0));

//        return items;
    } // end of function generateTodayEventsTestData()


    protected void setEvents(final ArrayList<Event> events) {
        mEvents = events;
    }

    /**
     * Generates past events list
     * @return
     */
    private ArrayList<Event> generatePastEvents(){

        // TODO: DB Call to get event objects

        ArrayList<Event> items = new ArrayList<Event>();
//        items.add(new ListItemEvent("Chipotle", "Attending: You, Michelle, Ryan",
//                "23rd St. Chipotle", "5:00 PM", new Date(114, 10, 5), 5, ListItemEvent.EventType.Past));

        return items;
    } // end of function generatePastEventsTestData()

    /**
     * Generates future events list
     * @return
     */
    private ArrayList<Event> generateFutureEvents(){

        // TODO: DB Call to get event objects

        ArrayList<Event> items = new ArrayList<Event>();
//        items.add(new ListItemEvent("Night out on Mass", "Attending: You, Adam, Kendal",
//                "Mass Street", "11:00 PM", new Date(114, 11, 6), 2, ListItemEvent.EventType.Future));
//        items.add(new ListItemEvent("Chipotle", "Attending: You, Michelle, Ryan",
//                "23rd St. Chipotle", "5:00 PM",new Date(114, 11, 7), 5, ListItemEvent.EventType.Future));
//        items.add(new ListItemEvent("Senior Design Meetup", "Attending: You, Adam, Kendal",
//                "Spahr Library", "7:00 PM",new Date(114, 11, 7), 0, ListItemEvent.EventType.Future));

        return items;
    } // end of function generateFutureEventsTestData()

} // end of class EventListSwipe
