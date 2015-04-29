package com.localhop.objects;


import java.util.Date;

public class UserLocation {

    private double mLat;
    private double mLong;
    private Date mLastUpdate;

    public UserLocation(double latitude, double longitude, Date lastUpdate)
    {
        this.mLat = latitude;
        this.mLong = longitude;
        this.mLastUpdate = lastUpdate;
    }

    public double getLong() {
        return mLong;
    }

    public double getLat() {
        return mLat;
    }

    public Date getLastUpdate() {
        return mLastUpdate;
    }

}
