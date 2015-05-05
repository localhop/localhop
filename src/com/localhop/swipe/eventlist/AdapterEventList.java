package com.localhop.swipe.eventlist;

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
import java.util.Date;

/**
 * Adapter for displaying custom list items in the Event List tab
 */
public class AdapterEventList extends ArrayAdapter<Event> {

    private final Context mContext;
    private final ArrayList<Event> mEventListItems;
    private final String mEventNameSpacing;             //< Spacing for the Event Name UI Component


    /**
     * Constructor
     * @param context - The context the adapter is being used in
     * @param itemsArrayList - List of items for the Custom ListView
     */
    public AdapterEventList(Context context, ArrayList<Event> itemsArrayList) {

        super(context, R.layout.list_item_event, itemsArrayList);

        this.mContext = context;
        this.mEventListItems = itemsArrayList;
        this.mEventNameSpacing = "    ";

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
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Create the view to be returned
        View rowView = setViewLayout(position, parent, inflater,
                mEventListItems.get(position).getType());

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
    public Event getItem(int position){
        return mEventListItems.get(position);
    } // end of function getItem()

    /**
     * Gets the view for an Event List Item
     * @param position
     * @param  rowView
     * @return
     */
    public void setItemView(int position, View rowView) {

        // Get the UI components
        TextView tvStartTime    = ViewUtils.findViewById(rowView, R.id.tvStartTime);
        TextView tvName         = ViewUtils.findViewById(rowView, R.id.tvName);
        TextView tvAttendees    = ViewUtils.findViewById(rowView, R.id.tvAttendees);
        TextView tvDirection    = ViewUtils.findViewById(rowView, R.id.tvDirection);
        TextView tvNotification = ViewUtils.findViewById(rowView, R.id.tvNotification);
        ImageButton ibDirection = ViewUtils.findViewById(rowView, R.id.ibDirections);

        // Get the current event
        Event event = mEventListItems.get(position);

        // Set the Event Time
        DateTime datetime = new DateTime(getContext(), event.getStartDateTime());
        tvStartTime.setText(datetime.getTimeFormat());

        // Set the remaining UI components
        tvName.setText(mEventNameSpacing + event.getEventName());

        // Set the attendees
        ArrayList<Friend> attendeeList = event.getAttendees();
        int attendeeCount = 0;

        String attendees = "";
        if(attendeeList.size() > 0){
            attendees += "Attending:  ";
        }
        for(int i = 0; i < attendeeList.size(); i++){
            Friend attendee = attendeeList.get(i);

            // Check if attendee is attending
            if(attendee.getAttendStatus() == 1){

                // Add a comma if there is an attendee preceding the next one
                if(attendeeCount > 0) {
                    attendees += ", ";
                }

                attendees += attendee.getFirstName();
                attendeeCount++;
            }

        }
        tvAttendees.setText(attendees);

        tvDirection.setText(event.getLocation());
        ibDirection.setBackgroundResource(R.drawable.ic_directions_selector);
        ibDirection.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button Click
            }
        });
        String sNotificationCount = "" + event.getNotificationCount();
        if ( sNotificationCount.compareTo("0") == 0 ) {
            sNotificationCount = "";
        }
        tvNotification.setText(sNotificationCount);

        // Add Date delimiter UI if the event is not today
        if(event.getType() != Event.EventType.Today)
        {
          if(position > 0)
          {
              DateTime dateTime = new DateTime(mContext, new Date());

              if(!dateTime.compareAreDatesSameDay(mEventListItems.get(position).getStartDateTime(),
                      mEventListItems.get(position - 1).getStartDateTime()))
              {
                  TextView tvEventListDateDelimiter = ViewUtils.findViewById(rowView, R.id.tvEventListDateDelimiter);
                  // Set the date into the format DayOfWeek, Month/Day, Year
                  tvEventListDateDelimiter.setText(
                          datetime.getDayOfWeekString() + ", " +
                                  datetime.getMonthDayYearFormat());
              }
          }
          else
          {
              TextView tvEventListDateDelimiter = ViewUtils.findViewById(rowView, R.id.tvEventListDateDelimiter);
              // Set the date into the format DayOfWeek, Month/Day, Year
              tvEventListDateDelimiter.setText(
                      datetime.getDayOfWeekString() + ", " +
                              datetime.getMonthDayYearFormat());
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
                              Event.EventType type) {

        int layout = R.layout.list_item_event;

        // If the event is not today, there isn't already an event listed on the same day, post this
        // event with the date delimiter layout
        if (type != Event.EventType.Today)
        {
            if( position > 0 )
            {
                DateTime dateTime = new DateTime(mContext, new Date());

                if(!dateTime.compareAreDatesSameDay(mEventListItems.get(position).getStartDateTime(),
                        mEventListItems.get(position - 1).getStartDateTime()))
                {
                    layout = R.layout.list_item_event_with_date_delimiter;
                }
            }
            else
            {
                layout = R.layout.list_item_event_with_date_delimiter;
            }
        }

        View rowView = inflater.inflate(layout, parent, false);

        return rowView;
    } // end of function setViewLayout()

} // end of class AdapterEventList.java
