package com.localhop;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Activity_TabEventList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_events_list);

        // Pass context and data to the custom adapter
        Adapter_EventList adapter = new Adapter_EventList(this, generateTestData());

        // Get ListView from tab_events_list.xml
        ListView lvEvents = (ListView)findViewById(R.id.lvEvents);

        // Set the List Adapter
        lvEvents.setAdapter(adapter);


    } // end of function onCreate()

    private ArrayList<Item_Event> generateTestData(){

        ArrayList<Item_Event> items = new ArrayList<Item_Event>();
        items.add(new Item_Event("Chipotle", "Attending: You, Michelle, Ryan", "23rd St. Chipotle", "5:00 PM", 5));
        items.add(new Item_Event("Senior Design Meetup", "Attending: You, Adam, Kendal", "Spahr Library", "7:00 PM", 0));
        items.add(new Item_Event("Drinks at Brothers", "Attending: Alexander, Greene, Traylor", "Brother's Bar", "8:00 PM", 1));
        items.add(new Item_Event("Dempsey's Half Pri...", "Attending: You, Michelle, Ryan", "Dempsey's", "9:00 PM", 1));
        items.add(new Item_Event("Night out on Mass", "Attending: You, Adam, Kendal", "Mass Street", "11:00 PM", 2));
        items.add(new Item_Event("Fuzzy's Tacos", "Attending: You, Adam, Kendal", "Fuzzy's Tacos", "1:00 AM", 0));

        return items;
    }

} // end of class TabEventListActivity
