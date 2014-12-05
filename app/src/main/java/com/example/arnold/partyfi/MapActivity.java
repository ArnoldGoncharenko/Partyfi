package com.example.arnold.partyfi;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.*;  // GoogleMap, CameraUpdateFactory
import com.google.android.gms.maps.model.*;  // Marker, MarkerOptions

public class MapActivity extends FragmentActivity {
    static final LatLng Toronto = new LatLng(43.6532, -79.38318429999998);
    private GoogleMap googleMap;

    PDBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {

            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.setMyLocationEnabled(true);

             UiSettings mapSettings;
            mapSettings = googleMap.getUiSettings();
            mapSettings.setCompassEnabled(true);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Toronto, 9);
            googleMap.animateCamera(cameraUpdate);
            addPartyMarkers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.item2 :
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            //noinspection SimplifiableIfStatement
            case R.id.action_settings :
                return true;
            case R.id.item3 :
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.item4 :
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    public void addPartyMarkers()
    {
        PDBAdapter db = new PDBAdapter(this);

        db.open();
        Cursor c = db.getAllParties();
        if (c.moveToFirst())
        {
            do {
//                String temp = new String ("Description : " + c.getString(3) + "\n" +
//                                          "Address : " + c.getString(5) + "\n" +
//                                          "Time of Party : " + c.getString(7) + "\n" +
//                                          "Date of Party : " + c.getString(6));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(c.getDouble(1), c.getDouble(2)))
                        .title(c.getString(4))
                        .snippet(c.getString(3)));


            } while (c.moveToNext());
        }
        db.close();
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            return rootView;
        }
    }
}
