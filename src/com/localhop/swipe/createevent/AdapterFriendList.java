package com.localhop.swipe.createevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.localhop.R;
import com.localhop.objects.DateTime;
import com.localhop.objects.Event;
import com.localhop.objects.Friend;
import com.localhop.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Adapter for displaying custom list items in the Create Event Invite Friend tab
 */
public class AdapterFriendList extends ArrayAdapter<Friend> {

    private final Context mContext;
    private final ArrayList<Friend> mFriendItems;

    public AdapterFriendList(Context context, ArrayList<Friend> itemsArrayList) {

        super(context, R.layout.item_friend, itemsArrayList);

        this.mContext = context;
        this.mFriendItems = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_friend, parent, false);

        // Get the UI components
        TextView tvFriendName = (TextView) rowView.findViewById(R.id.friend_list_item_friend_name);

        // Set the UI components
        tvFriendName.setText(mFriendItems.get(position).getsName());

        // Return the item
        return rowView;
    } // end of function getView()

}  // end of class AdapterFriendList.java