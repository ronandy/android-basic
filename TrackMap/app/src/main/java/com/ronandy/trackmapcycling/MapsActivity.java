package com.ronandy.trackmapcycling;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Point points;
    private Marker currentMarker;
    private Polyline polyline;
    myPosition myPos;
    private String user;

    private static final String TAG = "TRACKMAPS";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LONG = "long";
    private Firebase ref;
    private int count = 0;

    @Bind(R.id.user)
    EditText userTrack;

    @Bind(R.id.track)
    Button track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // my Position
        seePosition();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.track)
    public void seePosition() {
        getData(userTrack.getText().toString());
    }

    private void getData(final String userT) {
        String FIREBASE_URL = "https://trackmaps.firebaseio.com/"+userT;
        ref = new Firebase(FIREBASE_URL);
        myPos = new myPosition(this,userT);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG, "user: " + userT);
                Double plat = (Double) snapshot.child(TAG_LAT).getValue();
                Double plong = (Double) snapshot.child(TAG_LONG).getValue();
                Log.d(TAG, "lat: " + plat);
                Log.d(TAG, "long: " + plong);
                points = new Point(plat, plong, userT);
                CameraUpdate cu = CameraUpdateFactory.newLatLng(points.getLatLng());
                mMap.animateCamera(cu);

                count++;
                if (currentMarker == null) {
                    currentMarker = mMap.addMarker(new MarkerOptions().position(points.getLatLng()).title(userT));
                } else {
                    if (count == 2) {
                        polyline = mMap.addPolyline(new PolylineOptions().add(currentMarker.getPosition(), points.getLatLng()));
                    } else {
                        List<LatLng> pointsLine = polyline.getPoints();
                        pointsLine.add(points.getLatLng());
                        polyline.setPoints(pointsLine);
                    }
                    currentMarker.setPosition(points.getLatLng());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Location location = myPos.getLocation();
        //LatLng casa = new LatLng(location.getLatitude(),location.getLongitude());
        Double latitude = -17.375895;
        Double longitude = -66.165319;
        LatLng inicio = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(inicio).title("Punto inicial"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 15f));
    }

}
