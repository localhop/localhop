package com.localhop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for displaying custom list items in the Event List tab
 */
public class AdapterEventList extends ArrayAdapter<Item_Event> {

    private final Context context;
    private final ArrayList<Item_Event> itemsArrayList;
    private String eventNameSpacing = "    "; //< Spacing for the Event Name UI Component

    /**
     * Constructor
     * @param context - The context the adapter is being used in
     * @param itemsArrayList - List of items for the Custom ListView
     */
    public AdapterEventList(Context context, ArrayList<Item_Event> itemsArrayList) {

        super(context, R.layout.item_event, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    } // end of Constructor

    /**
     * Retrieves a single custom list item view for a particular event in the Events List
     * @param position - current Event from the itemsArrayList
     * @param convertView
     * @param parent
     * @return - list item view for a particular event in the Events List
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_event, parent, false);

        // Get the UI components
        TextView tvStartTime = (TextView) rowView.findViewById(R.id.tvStartTime);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvAttendees = (TextView) rowView.findViewById(R.id.tvAttendees);
        TextView tvDirection = (TextView) rowView.findViewById(R.id.tvDirection);
        TextView tvNotification = (TextView) rowView.findViewById(R.id.tvNotification);
        ImageButton ibDirection = (ImageButton) rowView.findViewById(R.id.ibDirections);

        // Set the UI components
        tvStartTime.setText(itemsArrayList.get(position).getsStartTime());
        tvName.setText(eventNameSpacing + itemsArrayList.get(position).getsEventName());
        tvAttendees.setText(itemsArrayList.get(position).getsAttendees());
        tvDirection.setText(itemsArrayList.get(position).getsLocation());
        ibDirection.setBackgroundResource(R.drawable.ic_directions_selector);

        String sNotificationCount = "" + itemsArrayList.get(position).getnNotificationCount();
        if ( sNotificationCount.compareTo("0") == 0 ) {
            sNotificationCount = "";
        }
        tvNotification.setText(sNotificationCount);

        return rowView;
    } // end of function getView()

} // end of class Adapter_EventList.java
