package com.localhop.swipe.createevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.localhop.R;
import com.localhop.objects.Group;

import java.util.ArrayList;

/**
 * Adapter for displaying custom list items in the Create Event Invite Groups tab
 */
public class AdapterGroupList extends ArrayAdapter<Group> {

    private final Context mContext;
    private final ArrayList<Group> mGroupItems;

    public AdapterGroupList(Context context, ArrayList<Group> itemsArrayList) {

        super(context, R.layout.item_friend, itemsArrayList);

        this.mContext = context;
        this.mGroupItems = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_group, parent, false);

        // Get the UI components
        TextView tvGroupName = (TextView) rowView.findViewById(R.id.group_list_item_group_name);
        TextView tvGroupList = (TextView) rowView.findViewById(R.id.group_list_item_group_members);

        // Set the UI components
        tvGroupName.setText(mGroupItems.get(position).getsName());
        tvGroupList.setText(mGroupItems.get(position).getsMembers());

        // Return the item
        return rowView;
    } // end of function getView()

}  // end of class AdapterGroupList.java