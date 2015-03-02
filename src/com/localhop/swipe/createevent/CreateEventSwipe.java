package com.localhop.swipe.createevent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.localhop.R;
import com.localhop.objects.Friend;
import com.localhop.objects.Group;
import com.localhop.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class CreateEventSwipe extends Fragment {

    private int mCurrentPage;
    private ArrayList<Friend> mFriends;
    private ArrayList<Group> mGroups;
    private View mCreateEventView;

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

        switch ( mCurrentPage ) {

            case 0:
                mCreateEventView = inflater.inflate(R.layout.tab_create_event_details, container, false);
                break;
            case 1:
                mCreateEventView = inflater.inflate(R.layout.tab_create_event_invite, container, false);

                mFriends = getFriends();
                mGroups = getGroups();

                final AdapterExpandFriendGroupList expandAdapter =
                        new AdapterExpandFriendGroupList(mCreateEventView.getContext(), mGroups, mFriends);

                ExpandableListView elvInvite = ViewUtils.findViewById(mCreateEventView, R.id.elvCreateEventInvite);
                elvInvite.setAdapter(expandAdapter);
                elvInvite.expandGroup(0);
                elvInvite.expandGroup(1);

                elvInvite.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                        //TODO: Do Something

                        return false;
                    }
                });

                break;
        }

        return mCreateEventView;
    } // end of function onCreateView()


    private ArrayList<Friend> getFriends() {

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
    } // end of function getFriends()

    private ArrayList<Group> getGroups() {

        ArrayList<Group> items = new ArrayList<Group>();
        items.add(new Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    } // end of function getGroups()

} // end of class EventListSwipe