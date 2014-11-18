package com.localhop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.localhop.AdapterEventList;
import com.localhop.ListItemEvent;
import com.localhop.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class EventListSwipe extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create the view for the event list items to be returned
        View eventListItems = inflater.inflate(R.layout.tab_events_list_view, container, false);

        // Determine which layout to load based on the swipe tab position
        ArrayList<ListItemEvent> events = null;
        ListItemEvent.EventType eventType = null;
        switch (mCurrentPage) {
            case 0:
                // Past Events
                eventType = ListItemEvent.EventType.Past;
                events = generatePastEventsTestData();
                break;
            case 1:
                // Today Events
                eventType = ListItemEvent.EventType.Today;
                events = generateTodayEventsTestData();
                break;
            case 2:
                // Future Events
                eventType = ListItemEvent.EventType.Future;
                events = generateFutureEventsTestData();
                break;
        }

        // Pass context and data to the custom adapter
        final AdapterEventList adapter = new AdapterEventList(eventListItems.getContext(),
                events, eventType);

        // Get ListView from tab_events_list_swipe.xml
        ListView lvEvents = (ListView)eventListItems.findViewById(R.id.lvEvents);

        // Set the List Adapter
        lvEvents.setAdapter(adapter);

        // Set the ListView Item Listener
        lvEvents.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("\n\nEvent " + adapter.getItem(position).getEventName() + " has been clicked!");
            }
        });

        return eventListItems;
    } // end of function onCreateView()

    /**
    * Generates static, test data for the Today Events List.  !! This will eventually be replaced
    * with DB stored procedure calls
    * @return
            */
    private ArrayList<ListItemEvent> generateTodayEventsTestData(){

        ArrayList<ListItemEvent> items = new ArrayList<ListItemEvent>();
        items.add(new ListItemEvent("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM", null, 5, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Senior Design Meetup", "Attending: You, Adam, Kendal",
                "Spahr Library", "7:00 PM", null, 0, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Drinks at Brothers", "Attending: Alexander, Greene, Traylor",
                "Brother's Bar", "8:00 PM", null, 1, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Dempsey's Half Pri...", "Attending: You, Michelle, Ryan",
                "Dempsey's", "9:00 PM", null, 1, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Night out on Mass", "Attending: You, Adam, Kendal",
                "Mass Street", "11:00 PM", null, 2, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Fuzzy's Tacos", "Attending: You, Adam, Kendal",
                "Fuzzy's Tacos", "1:00 AM", null, 0, ListItemEvent.EventType.Today));

        return items;
    } // end of function generateTodayEventsTestData()


    /**
     * Generates static, test data for the Past Events List.  !! This will eventually be replaced
     * with DB stored procedure calls
     * @return
     */
    private ArrayList<ListItemEvent> generatePastEventsTestData(){

        ArrayList<ListItemEvent> items = new ArrayList<ListItemEvent>();
        items.add(new ListItemEvent("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM", new Date(114, 10, 5), 5, ListItemEvent.EventType.Past));

        return items;
    } // end of function generatePastEventsTestData()

    /**
     * Generates static, test data for the Future Events List.  !! This will eventually be replaced
     * with DB stored procedure calls
     * @return
     */
    private ArrayList<ListItemEvent> generateFutureEventsTestData(){

        ArrayList<ListItemEvent> items = new ArrayList<ListItemEvent>();
        items.add(new ListItemEvent("Night out on Mass", "Attending: You, Adam, Kendal",
                "Mass Street", "11:00 PM", new Date(114, 11, 6), 2, ListItemEvent.EventType.Today));
        items.add(new ListItemEvent("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM",new Date(114, 11, 7), 5, ListItemEvent.EventType.Future));
        items.add(new ListItemEvent("Senior Design Meetup", "Attending: You, Adam, Kendal",
                "Spahr Library", "7:00 PM",new Date(114, 11, 7), 0, ListItemEvent.EventType.Future));

        return items;
    } // end of function generateFutureEventsTestData()

} // end of class EventListSwipe
