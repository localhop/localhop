package com.localhop.activities.event;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.localhop.swipe.createevent.AdapterFriendList;
import com.localhop.swipe.createevent.AdapterGroupList;
import com.localhop.objects.Friend;
import com.localhop.objects.Group;
import com.localhop.swipe.createevent.CreateEventSwipeAdapter;
import java.util.ArrayList;

import com.localhop.R;

public class ActivityTabCreateEvent extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tab_create_event_invite);

        setContentView(R.layout.tab_create_event_swipe);

        // Pass context and data to the custom adapter
        AdapterGroupList groupAdapter = new AdapterGroupList(this, generateGroupData());
        AdapterFriendList friendAdapter = new AdapterFriendList(this, generateFriendData());

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

    private ArrayList<Group> generateGroupData(){

        ArrayList<Group> items = new ArrayList<Group>();
        items.add(new Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    }

    private ArrayList<Friend> generateFriendData(){

        ArrayList<Friend> items = new ArrayList<Friend>();
        items.add(new Friend("Adam Smith"));
        items.add(new Friend("Kendal Harland"));
        items.add(new Friend("Michelle Perz"));
        items.add(new Friend("Ryan Scott"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Zach Flies"));
        items.add(new Friend("Ryan Scott"));



        return items;
    }

    /**
     * Sets up the custom Swipe View for displaying Past, Today, and Future events
     */
    void setupSwipeView() {

        ViewPager pager = (ViewPager) findViewById(R.id.pCreateEventDetailInvite);
        FragmentManager fm = getSupportFragmentManager();
        CreateEventSwipeAdapter pagerAdapter = new CreateEventSwipeAdapter(fm);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);

    } // end of function setupSwipeView()

} // end of class TabEventListActivity
