package com.localhop.objects;


public class Friend {

    // Variables subject to change once
    // DB integration starts
    private String sMembers;
    private String sName;

    public Friend(String sName) {

        super();
        this.sName = sName;

    } // end of constructor


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

} // end of class Event