package com.localhop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_TabCreateEvent extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_create_event_invite);

        // Pass context and data to the custom adapter
        Adapter_GroupList adapter = new Adapter_GroupList(this, generateTestData());

        // Get ListView from tab_events_list.xml
        ListView lvEvents = (ListView)findViewById(R.id.event_invite_group_list);

        // Set the List Adapter
        lvEvents.setAdapter(adapter);


    } // end of function onCreate()

    private ArrayList<Item_Group> generateTestData(){

        ArrayList<Item_Group> items = new ArrayList<Item_Group>();
        items.add(new Item_Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Item_Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Item_Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    }

} // end of class TabEventListActivity
