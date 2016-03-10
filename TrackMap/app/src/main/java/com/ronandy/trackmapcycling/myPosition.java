package com.ronandy.trackmapcycling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.firebase.client.Firebase;

/**
 * Created by Andy on 14/01/2016.
 */
public class myPosition extends Activity implements LocationListener {
    private Context ctx;
    LocationManager locationManager;
    String provider;
    String user;
    private boolean networkOn;
    private Location loc;

    public myPosition(Context context, String user) {
        ctx = context;
        this.user = user;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;
        networkOn = locationManager.isProviderEnabled(provider);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
        }
        locationManager.requestLocationUpdates(provider, 1000, 1, this);
    }

    public Location getLocation(){
        if (networkOn){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                }
            }
            loc = locationManager.getLastKnownLocation(provider);
            if(loc != null){
                System.out.println("Ready location... lat: " + String.valueOf(loc.getLatitude()) + " long: " + String.valueOf(loc.getLongitude()));
                String FIREBASE_URL = "https://trackmaps.firebaseio.com/"+user;
                Firebase ref = new Firebase(FIREBASE_URL);
                ref.child("lat").setValue(loc.getLatitude());
                ref.child("long").setValue(loc.getLongitude());
            } else {
                //loc.setLatitude(-17.370689);
                //loc.setLongitude(-66.166923);
                System.out.println("Failed location... ");
            }
        }
        return loc;
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
