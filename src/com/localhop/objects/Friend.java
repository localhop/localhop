package com.localhop.objects;


import org.json.JSONException;
import org.json.JSONObject;

public class Friend {

    // Variables subject to change once
    // DB integration starts
    private String mFullName;
    private String mFirstName;
    private String mLastName;
    private int mID;
    private int mBroadcast;
    private int mAttendStatus;
    private UserLocation mLocation;

    public Friend(String sName) {

        super();
        this.mFullName = sName;

    } // end of constructor

    public Friend(int id, String firstName, String lastName, int broadcast, int attendStatus) {

        super();
        this.mFullName = firstName + " " + lastName;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mID = id;
        this.mBroadcast = broadcast;
        this.mAttendStatus = attendStatus;

    } // end of constructor

    public String getFirstName() {
        return mFirstName;
    }
    public String getFullName() {
        return mFullName;
    }

    public int getBroadcast() { return mBroadcast; }
    public int getAttendStatus() { return mAttendStatus; }
    public int getID() { return mID; }
    public UserLocation getLocation(){return  mLocation;}


    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }
    public void setLocation(UserLocation location){this.mLocation = location;}

    /**
     * Collects Friend objects from the DB
     * @param o
     * @return
     */
    public static Friend fromJSON(JSONObject o) {
        try {
            final String name          = o.getString("name_first") + " " + o.getString("name_last");

            return new Friend(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO: evil null voodoo
        }
    }

    /**
     * Collects Friend objects from the DB
     * @param o
     * @return
     */
    public static Friend fromJSON(JSONObject o, String override) {
        try {
            final String firstName = o.getString("name_first");
            final String lastName = o.getString("name_last");
            final int id = o.getInt("id");
            final int broadcast = o.getInt("broadcast");
            final int attendStatus = o.getInt("attend_status");

            return new Friend(id, firstName, lastName, broadcast, attendStatus);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO: evil null voodoo
        }
    }

} // end of class Friend