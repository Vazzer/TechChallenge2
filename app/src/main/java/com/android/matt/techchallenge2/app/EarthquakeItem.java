package com.android.matt.techchallenge2.app;

/**
 * Created by Matt on 12/7/2015.
 */
public class EarthquakeItem {

    public String title;
    public String latitude;
    public String longitude;
    public String time;
    public String shakeURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShakeURL() {
        return shakeURL;
    }

    public void setShakeURL(String shakeURL) {
        this.shakeURL = shakeURL;
    }
}
