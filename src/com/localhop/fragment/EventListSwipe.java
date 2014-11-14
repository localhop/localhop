package com.localhop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.localhop.AdapterEventList;
import com.localhop.Item_Event;
import com.localhop.R;

import java.util.ArrayList;

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

        View eventListItem = inflater.inflate(R.layout.tab_events_list_today, container,false);

        switch (mCurrentPage) {
            case 0:
                // Past Events
                eventListItem = inflater.inflate(R.layout.tab_events_list_today, container,false);
                setEventsPage(eventListItem);
                break;
            case 1:
                // Today Events
                setEventsPage(eventListItem);
                break;
            case 2:
                // Future Events
                eventListItem = inflater.inflate(R.layout.tab_events_list_today, container,false);
                setEventsPage(eventListItem);
                break;
        }

        return eventListItem;
    } // end of function onCreateView()

    /**
     * Sets the screen for the Today events list
     */
    private void setEventsPage(View v) {

        // Pass context and data to the custom adapter
        AdapterEventList adapter = new AdapterEventList(v.getContext(),
                generateTodayEventsTestData());

        switch (mCurrentPage) {
            case 0:
                // Past Events
                adapter = new AdapterEventList(v.getContext(),
                        generatePastEventsTestData());
                break;
            case 1:
                // Today Events
                break;
            case 2:
                // Future Events
                adapter = new AdapterEventList(v.getContext(),
                        generateFutureEventsTestData());
                break;
        }

        // Get ListView from tab_events_list.xml
        ListView lvEvents = (ListView)v.findViewById(R.id.lvEvents);

        // Set the List Adapter
        lvEvents.setAdapter(adapter);

    } // end of function setTodayEventsPage()

    /**
    * Generates static, test data for the Today Events List.  !! This will eventually be replaced
    * with DB stored procedure calls
    * @return
            */
    private ArrayList<Item_Event> generateTodayEventsTestData(){

        ArrayList<Item_Event> items = new ArrayList<Item_Event>();
        items.add(new Item_Event("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM", 5));
        items.add(new Item_Event("Senior Design Meetup", "Attending: You, Adam, Kendal",
                "Spahr Library", "7:00 PM", 0));
        items.add(new Item_Event("Drinks at Brothers", "Attending: Alexander, Greene, Traylor",
                "Brother's Bar", "8:00 PM", 1));
        items.add(new Item_Event("Dempsey's Half Pri...", "Attending: You, Michelle, Ryan",
                "Dempsey's", "9:00 PM", 1));
        items.add(new Item_Event("Night out on Mass", "Attending: You, Adam, Kendal",
                "Mass Street", "11:00 PM", 2));
        items.add(new Item_Event("Fuzzy's Tacos", "Attending: You, Adam, Kendal",
                "Fuzzy's Tacos", "1:00 AM", 0));

        return items;
    } // end of function generateTodayEventsTestData()


    /**
     * Generates static, test data for the Today Events List.  !! This will eventually be replaced
     * with DB stored procedure calls
     * @return
     */
    private ArrayList<Item_Event> generatePastEventsTestData(){

        ArrayList<Item_Event> items = new ArrayList<Item_Event>();
        items.add(new Item_Event("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM", 5));

        return items;
    } // end of function generateTodayEventsTestData()

    /**
     * Generates static, test data for the Future Events List.  !! This will eventually be replaced
     * with DB stored procedure calls
     * @return
     */
    private ArrayList<Item_Event> generateFutureEventsTestData(){

        ArrayList<Item_Event> items = new ArrayList<Item_Event>();
        items.add(new Item_Event("Chipotle", "Attending: You, Michelle, Ryan",
                "23rd St. Chipotle", "5:00 PM", 5));
        items.add(new Item_Event("Senior Design Meetup", "Attending: You, Adam, Kendal",
                "Spahr Library", "7:00 PM", 0));

        return items;
    } // end of function generatePastEventsTestData()

} // end of class EventListSwipe
