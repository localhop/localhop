package com.localhop;

public class Item_Group {

    // Variables subject to change once
    // DB integration starts
    private String sAttendees;
    private String sEventName;
    private String sLocation;
    private String sStartTime;
    private int nNotificationCount;

    public Item_Group(String sEventName, String sAttendees, String sLocation,
                      String sStartTime, int nNotificationCount) {

        super();
        this.sEventName = sEventName;
        this.sAttendees = sAttendees;
        this.sLocation = sLocation;
        this.sStartTime = sStartTime;
        this.nNotificationCount = nNotificationCount;

    } // end of constructor


    public String getsAttendees() {
        return sAttendees;
    }

    public void setsAttendees(String sAttendees) {
        this.sAttendees = sAttendees;
    }

    public String getsEventName() {
        return sEventName;
    }

    public void setsEventName(String sEventName) {
        this.sEventName = sEventName;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public String getsStartTime() {
        return sStartTime;
    }

    public void settStartTime(String sStartTime) {
        this.sStartTime = sStartTime;
    }

    public int getnNotificationCount() {
        return nNotificationCount;
    }

    public void setnNotificationCount(int nNotificationCount) {
        this.nNotificationCount = nNotificationCount;
    }

} // end of class Item_Event
