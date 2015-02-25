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


public class AdapterGroupList extends ArrayAdapter<Group> {

    private final Context context;
    private final ArrayList<Group> itemsArrayList;
    private String sEventNameSpacing = "    ";

    public AdapterGroupList(Context context, ArrayList<Group> itemsArrayList) {

        super(context, R.layout.item_friend, itemsArrayList);

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