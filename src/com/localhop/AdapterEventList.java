package com.localhop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for displaying custom list items in the Event List tab
 */
public class AdapterEventList extends ArrayAdapter<ListItemEvent> {

    private final Context context;
    private final ArrayList<ListItemEvent> itemsArrayList;
    private final ListItemEvent.EventType eventType;
    private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday"};
    private String eventNameSpacing = "    "; //< Spacing for the Event Name UI Component

    /**
     * Constructor
     * @param context - The context the adapter is being used in
     * @param itemsArrayList - List of items for the Custom ListView
     */
    public AdapterEventList(Context context, ArrayList<ListItemEvent> itemsArrayList, ListItemEvent.EventType eventType) {

        super(context, R.layout.list_item_event, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.eventType = eventType;
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

        View rowView = null;

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get the date from the current Event
        Date date = itemsArrayList.get(position).getStartDate();

        // Determine how to list the next list item
        if(eventType == ListItemEvent.EventType.Future) {
            if (position > 0) {
                if (date.compareTo(itemsArrayList.get(position - 1).getStartDate()) != 0) {
                    rowView = getDateDelimeterView(position, parent, inflater);
                } else {
                    rowView = getItemView(position, parent, inflater);
                }
            }
            else {
                rowView = getDateDelimeterView(position, parent, inflater);
            }

        }
        else {
            rowView = getItemView(position, parent, inflater);
        }

        return rowView;
    } // end of function getView()


    /**
     * Gets the view for a date delimited list item
     * @param position
     * @param parent
     * @param inflater
     * @return
     */
    public View getDateDelimeterView(int position, ViewGroup parent, LayoutInflater inflater) {

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item_event_date_delimeter, parent, false);

        // Get the UI components
        TextView tvStartTime = (TextView) rowView.findViewById(R.id.tvStartTime);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvAttendees = (TextView) rowView.findViewById(R.id.tvAttendees);
        TextView tvDirection = (TextView) rowView.findViewById(R.id.tvDirection);
        TextView tvNotification = (TextView) rowView.findViewById(R.id.tvNotification);
        ImageButton ibDirection = (ImageButton) rowView.findViewById(R.id.ibDirections);
        TextView tvEventListDateDelimiter = (TextView) rowView.findViewById(R.id.tvEventListDateDelimiter);

        // Set the UI components
        tvStartTime.setText(itemsArrayList.get(position).getStartTime());
        tvName.setText(eventNameSpacing + itemsArrayList.get(position).getEventName());
        tvAttendees.setText(itemsArrayList.get(position).getAttendees());
        tvDirection.setText(itemsArrayList.get(position).getLocation());
        ibDirection.setBackgroundResource(R.drawable.ic_directions_selector);
        String sNotificationCount = "" + itemsArrayList.get(position).getNotificationCount();
        if ( sNotificationCount.compareTo("0") == 0 ) {
            sNotificationCount = "";
        }
        tvNotification.setText(sNotificationCount);

        Date date = itemsArrayList.get(position).getStartDate();
        if (date != null) {
            tvEventListDateDelimiter.setText(daysOfWeek[date.getDay()] + ", " +
                    (date.getMonth() + 1) + "/" + date.getDate() + ", " +
                    (date.getYear() + 1900));
        }

        return rowView;
    } // end of function getDateDelimeterView()


    /**
     * Gets the view for an Event List Item
     * @param position
     * @param parent
     * @param inflater
     * @return
     */
    public View getItemView(int position, ViewGroup parent, LayoutInflater inflater) {

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item_event, parent, false);

        // Get the UI components
        TextView tvStartTime = (TextView) rowView.findViewById(R.id.tvStartTime);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvAttendees = (TextView) rowView.findViewById(R.id.tvAttendees);
        TextView tvDirection = (TextView) rowView.findViewById(R.id.tvDirection);
        TextView tvNotification = (TextView) rowView.findViewById(R.id.tvNotification);
        ImageButton ibDirection = (ImageButton) rowView.findViewById(R.id.ibDirections);

        // Set the UI components
        tvStartTime.setText(itemsArrayList.get(position).getStartTime());
        tvName.setText(eventNameSpacing + itemsArrayList.get(position).getEventName());
        tvAttendees.setText(itemsArrayList.get(position).getAttendees());
        tvDirection.setText(itemsArrayList.get(position).getLocation());
        ibDirection.setBackgroundResource(R.drawable.ic_directions_selector);

        String sNotificationCount = "" + itemsArrayList.get(position).getNotificationCount();
        if ( sNotificationCount.compareTo("0") == 0 ) {
            sNotificationCount = "";
        }
        tvNotification.setText(sNotificationCount);

        return rowView;
    } // end of function getItemView()

} // end of class Adapter_EventList.java
