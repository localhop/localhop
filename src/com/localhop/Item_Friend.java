package com.localhop;


public class Item_Friend {

    // Variables subject to change once
    // DB integration starts
    private String sMembers;
    private String sName;

    public Item_Friend(String sName) {

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