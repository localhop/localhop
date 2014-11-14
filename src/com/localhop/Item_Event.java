package com.localhop;

public class Item_Event {

    // Variables subject to change once
    // DB integration starts
    private String attendees;
    private String eventName;
    private String location;
    private String startTime;
    private int notificationCount;

    /**
     * Constructor
     * @param eventName
     * @param attendees
     * @param location
     * @param startTime
     * @param notificationCount
     */
    public Item_Event(String eventName, String attendees, String location,
                      String startTime, int notificationCount) {

        super();
        this.eventName = eventName;
        this.attendees = attendees;
        this.location = location;
        this.startTime = startTime;
        this.notificationCount = notificationCount;

    } // end of constructor


    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String sAttendees) {
        this.attendees = attendees;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String sEventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String sLocation) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String sStartTime) {
        this.startTime = startTime;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

} // end of class Item_Event
