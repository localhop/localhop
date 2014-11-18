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
    private final String[] daysOfWeek;
    private String eventNameSpacing = "    "; //< Spacing for the Event Name UI Component

    /**
     * Constructor
     * @param context - The context the adapter is being used in
     * @param itemsArrayList - List of items for the Custom ListView
     */
    public AdapterEventList(Context context, ArrayList<ListItemEvent> itemsArrayList,
                            ListItemEvent.EventType eventType) {

        super(context, R.layout.list_item_event, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.eventType = eventType;
        this.daysOfWeek = context.getResources().getStringArray(R.array.days_of_week);
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

        // Create the view to be returned
        View rowView = setViewLayout(position, parent, inflater,
                itemsArrayList.get(position).getType());

        // Setup the UI for the new list item view
        setItemView(position, rowView);

        return rowView;
    } // end of function getView()


    /**
     * Return the ListItemEvent object at the indicated position within itemsArrayList
     * @param position
     * @return
     */
    @Override
    public ListItemEvent getItem(int position){
        return itemsArrayList.get(position);
    } // end of function getItem()

    /**
     * Gets the view for an Event List Item
     * @param position
     * @param  rowView
     * @return
     */
    public void setItemView(int position, View rowView) {

        // Get the UI components
        TextView tvStartTime = (TextView) rowView.findViewById(R.id.tvStartTime);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvAttendees = (TextView) rowView.findViewById(R.id.tvAttendees);
        TextView tvDirection = (TextView) rowView.findViewById(R.id.tvDirection);
        TextView tvNotification = (TextView) rowView.findViewById(R.id.tvNotification);
        ImageButton ibDirection = (ImageButton) rowView.findViewById(R.id.ibDirections);

        // Set the UI components
        ListItemEvent event = itemsArrayList.get(position);
        tvStartTime.setText(event.getStartTime());
        tvName.setText(eventNameSpacing + event.getEventName());
        tvAttendees.setText(event.getAttendees());
        tvDirection.setText(event.getLocation());
        ibDirection.setBackgroundResource(R.drawable.ic_directions_selector);
        ibDirection.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button
            }
        });
        String sNotificationCount = "" + event.getNotificationCount();
        if ( sNotificationCount.compareTo("0") == 0 ) {
            sNotificationCount = "";
        }
        tvNotification.setText(sNotificationCount);

        // Add Date delimiter UI if the event is not today
        Date date = event.getStartDate();
        if(event.getType() != ListItemEvent.EventType.Today &&
                ((position > 0 && itemsArrayList.get(position).getStartDate()
                        .compareTo(itemsArrayList.get(position - 1).getStartDate()) != 0) ||
                        position <= 0)) {
            TextView tvEventListDateDelimiter = (TextView) rowView
                    .findViewById(R.id.tvEventListDateDelimiter);
            // Set the date into the format DayOfWeek, Month/Day, Year
            if (date != null) {
                tvEventListDateDelimiter.setText(daysOfWeek[date.getDay()] + ", " +
                        (date.getMonth() + 1) + "/" + date.getDate() + ", " +
                        (date.getYear() + 1900));
            }
        }

    } // end of function setItemView()


    /**
     * Sets the layout for the next list item View to be added to the list view
     * @param  position
     * @param parent
     * @param inflater
     * @param type
     * @return
     */
    public View setViewLayout(int position, ViewGroup parent, LayoutInflater inflater,
                              ListItemEvent.EventType type) {

        int layout = R.layout.list_item_event;

        if (type != ListItemEvent.EventType.Today &&
                ((position > 0 && itemsArrayList.get(position).getStartDate()
                        .compareTo(itemsArrayList.get(position - 1).getStartDate()) != 0) ||
                position <= 0)) {
            layout = R.layout.list_item_event_date_delimiter;
        }

        View rowView = inflater.inflate(layout, parent, false);

        return rowView;
    } // end of function setViewLayout()

} // end of class Adapter_EventList.java
