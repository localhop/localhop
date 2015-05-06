package com.localhop.objects;

import android.app.Activity;

import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This object class contains all the information associated with an event
 */
public class Event implements Serializable{

    /**
     * Distinguishes a time frame for a particular event
     */
    public enum EventType {
        Past,
        Today,
        Future
    }

    // Event variables
    private int eventID;                 //< Event id
    private EventType type;         //< Event Type (i.e. today, past, or future)
    private String name;            //< Event name
    private String description;     //< Event description
    private String location;        //< Event location
    private int inviteSetting;      //< Event Invite Setting
    private Date startDateTime;     //< Event's start Date/Time
    private Date endDateTime;       //< Event's end Date/Time
    private int organizer;          //< Event's organizer id (needed to derive name)
    private static ArrayList<Friend> attendees;//< Event's attendee ids and their location broadcast status
    private int notificationCount;  //< Number of notifications associated for this event for a user

    /**
     * Constructor - Set all event variables
     * @param id
     * @param type
     * @param name
     * @param description
     * @param location
     * @param attendees
     * @param startDateTime
     * @param endDateTime
     * @param inviteSetting
     * @param organizer
     * @param notificationCount
     */
    public Event(int id, EventType type, String name, String description, String location,
                 Date startDateTime, Date endDateTime, ArrayList<Friend> attendees,
                 int inviteSetting, int organizer, int notificationCount) {

        super();
        this.eventID = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.attendees = attendees;
        this.inviteSetting = inviteSetting;
        this.organizer = organizer;
        this.notificationCount = notificationCount;

    } // end of constructor

    /**
     * Constructor - Set all event variables, excluding the event id
     * @param type
     * @param name
     * @param description
     * @param location
     * @param attendees
     * @param startDateTime
     * @param endDateTime
     * @param inviteSetting
     * @param organizer
     * @param notificationCount
     */
    public Event(EventType type, String name, String description, String location,
                 Date startDateTime, Date endDateTime, ArrayList<Friend> attendees,
                 int inviteSetting, int organizer, int notificationCount) {

        super();
        this.type = type;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.attendees = attendees;
        this.inviteSetting = inviteSetting;
        this.organizer = organizer;
        this.notificationCount = notificationCount;

    } // end of constructor


    /**
     * Collects Event objects from the DB
     * @param o
     * @return
     */
    public static Event fromJSON(JSONObject o) {
        try {
            final SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            final int id               = o.getInt("id");
            final String name          = o.getString("name");
            final String description   = o.getString("description");
            final String location      = o.getString("location");

            String temp = o.getString("start");
            temp = temp.substring(0, 10) + " " + temp.substring(11, 19);
            Date startDateTime = newFormat.parse(temp);
            temp = o.getString("end");
            temp = temp.substring(0, 10) + " " + temp.substring(11, 19);
            Date endDateTime = newFormat.parse(temp);

            final int    inviteSetting = o.getJSONArray("invite_setting").getInt(0);
            final int    organizer     = o.getInt("org_user_id");

            // TODO: notification count

            // Determine if the event is Past, Today, or Future
            final Date   currentDate   = new Date();
            EventType type = EventType.Today;
            if(startDateTime.getTime() < currentDate.getTime() &&
                    startDateTime.getDate() != currentDate.getDate())
            {
                type = EventType.Past;
            }
            else if (startDateTime.getTime() > currentDate.getTime() &&
                    startDateTime.getDate() != currentDate.getDate())
            {
                type = EventType.Future;
            }

            // As of now, the attendees will be null. Due to static reference issues, we will have
            //  to call getAttendees() outside of this class.
            Event newEvent = new Event(id, type, name, description, location, startDateTime,
                    endDateTime, new ArrayList<Friend>(), inviteSetting, organizer, 0);

            return newEvent;

        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO: evil null voodoo
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // TODO: evil null voodoo
        }
    }

    /**
     * Serializes an Event object into a URL encoded form entity
     * @return String
     */
    public List<NameValuePair> toNameValuePair() {
        List<NameValuePair> data = new ArrayList<NameValuePair>() ;

        // Format the date for MySQL
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = sdf.format(this.startDateTime);
        String end = sdf.format(this.endDateTime);

        data.add(new BasicNameValuePair("name", this.name));
        data.add(new BasicNameValuePair("description", this.description));
        data.add(new BasicNameValuePair("location", this.location));
        data.add(new BasicNameValuePair("inviteSetting", Integer.toString(this.inviteSetting)));
        data.add(new BasicNameValuePair("start", start));
        data.add(new BasicNameValuePair("end", end));
        data.add(new BasicNameValuePair("userID", Integer.toString(this.organizer)));

        return data;
    } // end of function toNameValuePair()


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //    Getters and Setters
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Friend> getAttendees() {
        return attendees;
    }

    public void setAttendees(final ArrayList<Friend> attendees) {
        this.attendees = attendees;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDateTime() { return endDateTime;}
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getEventID(){return eventID;}

    public String getEventName() {
        return name;
    }
    public void setEventName(String eventName) {
        this.name = eventName;
    }

    public int getInviteSetting() {
        return inviteSetting;
    }
    public void setInviteSetting(int inviteSetting) {
        this.inviteSetting = inviteSetting;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNotificationCount() {
        return notificationCount;
    }
    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public int getOrganizer() {
        return organizer;
    }
    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }
    public Date getStartDateTime() {
        return startDateTime;
    }

    public EventType getType() {
        return type;
    }
    public void setType(EventType type) {
        this.type = type;
    }

} // end of class Item_Event
