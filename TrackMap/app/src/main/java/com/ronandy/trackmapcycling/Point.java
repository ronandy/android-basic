package com.ronandy.trackmapcycling;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Andy on 11/01/2016.
 */
public class Point {
    private LatLng latLng;
    private String name;

    public Point(double lat, double lng, String name) {
        this.latLng = new LatLng(lat, lng);
        this.name = name;
    }

    public Point(LatLng latLng, String name) {
        this.latLng = latLng;
        this.name = name;
    }

    public Point() {
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarkerOptions toMarker(){
        MarkerOptions mo = new MarkerOptions();
        mo.position(latLng);
        mo.title(name);
        return mo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Point{");
        sb.append("latLng=").append(latLng);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
