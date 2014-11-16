package com.localhop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_FriendList extends ArrayAdapter<Item_Group> {

    private final Context context;
    private final ArrayList<Item_Group> itemsArrayList;
    private String sEventNameSpacing = "    ";

    public Adapter_FriendList(Context context, ArrayList<Item_Group> itemsArrayList) {

        super(context, R.layout.item_event, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_group, parent, false);

        // Get the UI components
        TextView tvGroupName = (TextView) rowView.findViewById(R.id.group_list_item_group_name);
        TextView tvGroupList = (TextView) rowView.findViewById(R.id.group_list_item_group_members);

        // Set the UI components
        tvGroupName.setText(itemsArrayList.get(position).getsName());
        tvGroupList.setText(itemsArrayList.get(position).getsMembers());

        // Return the item
        return rowView;
    }
}