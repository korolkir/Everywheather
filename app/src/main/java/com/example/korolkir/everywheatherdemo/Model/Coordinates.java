package com.example.korolkir.everywheatherdemo.Model;

/**
 * Created by korolkir on 07.08.16.
 */
public class Coordinates {

    double lat;
    double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
