package com.localhop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity_TabCreateEvent extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_create_event_invite);

        // Pass context and data to the custom adapter
        Adapter_GroupList groupAdapter = new Adapter_GroupList(this, generateGroupData());
        Adapter_FriendList friendAdapter = new Adapter_FriendList(this, generateFriendData());

        // Get ListView from tab_create_event_invite.xml
        LinearLayout lvEventInviteGroups = (LinearLayout)findViewById(R.id.event_invite_group_list);
        LinearLayout lvEventInviteFriends = (LinearLayout)findViewById(R.id.event_invite_friend_list);

        // Set the List Adapter
//        lvEventInviteGroups.setAdapter(adapter);

        for (int i = 0; i < groupAdapter.getCount(); i++) {
            View item = groupAdapter.getView(i, null, null);
            lvEventInviteGroups.addView(item);
        }

        for (int i = 0; i < friendAdapter.getCount(); i++) {
            View item = friendAdapter.getView(i, null, null);
            lvEventInviteFriends.addView(item);
        }

    } // end of function onCreate()

    private ArrayList<Item_Group> generateGroupData(){

        ArrayList<Item_Group> items = new ArrayList<Item_Group>();
        items.add(new Item_Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Item_Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Item_Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    }

    private ArrayList<Item_Friend> generateFriendData(){

        ArrayList<Item_Friend> items = new ArrayList<Item_Friend>();
        items.add(new Item_Friend("Adam Smith"));
        items.add(new Item_Friend("Kendal Harland"));
        items.add(new Item_Friend("Michelle Perz"));
        items.add(new Item_Friend("Ryan Scott"));
        items.add(new Item_Friend("Zach Flies"));



        return items;
    }

} // end of class TabEventListActivity
