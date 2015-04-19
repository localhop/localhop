package com.localhop.objects;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Friend {

    // Variables subject to change once
    // DB integration starts
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

    /**
     * Collects Event objects from the DB
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

} // end of class Friend