package com.localhop.objects;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Group {

    // Variables subject to change once
    // DB integration starts
    private ArrayList<Friend> sMembers;
    private String sName;

    public Group(ArrayList<Friend> sMembers, String sName) {

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

    public ArrayList<Friend> getsMembers() {
        return sMembers;
    }

    public String getsMembersString() {
        StringBuilder strList = new StringBuilder();
        for (int i=0; i<sMembers.size()-1; i++) {
            strList.append(sMembers.get(i).getFullName() + ", ");
        }
        strList.append(sMembers.get(sMembers.size()-1).getFullName());

        return strList.toString();
    }

    public void setsMembers(ArrayList<Friend> sMembers) {
        this.sMembers = sMembers;
    }

    /**
     * Collects Group objects from the DB
     * @param o
     * @return
     */
    public static Group fromJSON(JSONObject o) {
        try {
            final String name          = o.getString("name");
            final JSONArray jsonFriends = o.getJSONArray("members");
            ArrayList<Friend> friends = new ArrayList<Friend>();
            for (int i=0; i< jsonFriends.length(); i++) {
                friends.add(new Friend(jsonFriends.getJSONObject(i).getString("name_first") + " " + jsonFriends.getJSONObject(i).getString("name_last")));
            }
            return new Group(friends, name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO: evil null voodoo
        }
    }

} // end of class Event