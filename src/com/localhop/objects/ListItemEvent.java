package com.localhop.objects;

import java.util.Date;

public class ListItemEvent {

    public enum EventType {
        Past,
        Today,
        Future
    }

    // Variables subject to change once
    // DB integration starts
    private String attendees;
    private String eventName;
    private String location;
    private String startTime;
    private Date startDate;
    private int notificationCount;
    private EventType type;

    /**
     * Constructor
     * @param eventName
     * @param attendees
     * @param location
     * @param startTime
     * @param startDate
     * @param notificationCount
     */
    public ListItemEvent(String eventName, String attendees, String location,
                         String startTime, Date startDate, int notificationCount, EventType type) {

        super();
        this.eventName = eventName;
        this.attendees = attendees;
        this.location = location;
        this.startTime = startTime;
        this.startDate = startDate;
        this.notificationCount = notificationCount;
        this.type = type;

    } // end of constructor


    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

} // end of class Item_Event
