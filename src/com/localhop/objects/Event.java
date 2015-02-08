package com.localhop.objects;

import java.io.Serializable;
import java.util.Date;

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
    private EventType type;         //< Event Type (i.e. today, past, or future)
    private String name;            //< Event name
    private String description;     //< Event description
    private String location;        //< Event location
    private int inviteSetting;      //< Event Invite Setting
    private Date startDateTime;     //< Event's start Date/Time
    private Date endDateTime;       //< Event's end Date/Time
    private int organizer;          //< Event's organizer id (need to derive name)
    private String attendees;       //< Event attendees (also derived)
    private int notificationCount;  //< Number of notifications associated for this event for a user


    /**
     * Constructor - Set all event variables
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
                 Date startDateTime, Date endDateTime, String attendees,
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

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //    Getters and Setters
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public String getAttendees() {
        return attendees;
    }
    public void setAttendees(String attendees) {
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
