package com.localhop;


public class Item_Friend {

    // Variables subject to change once
    // DB integration starts
    private String sMembers;
    private String sName;

    public Item_Friend(String sMembers, String sName) {

        super();
        this.sMembers = sMembers;
        this.sName = sName;

    } // end of constructor


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsMembers() {
        return sMembers;
    }

    public void setsMembers(String sMembers) {
        this.sMembers = sMembers;
    }

} // end of class Event