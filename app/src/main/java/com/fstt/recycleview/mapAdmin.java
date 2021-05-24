package com.fstt.recycleview;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mapAdmin extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private MapView mMapView;

    private ImageButton imageButton;
    private ImageButton imageButtonPath;
    private int i = 0;
    private  int t;
    private DatabaseReference mdatabase;
    private LinearLayout Rmap;
    private LinearLayout Rcontent;
    private FusedLocationProviderClient client;
    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyCoQLgtN4YIqmxrGZTg7ywab7BxzMT2iko";
    private double latutude;
    private double longitude;
    private double long_des;
    private double employee_lat;
    private double lat_des;
    private double employee_lon;
    private GeoApiContext geoApiContext = null;
    private String position_Emp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_admin);
        client = LocationServices.getFusedLocationProviderClient(this);
        imageButton = (ImageButton) findViewById(R.id.expand);
        imageButtonPath = (ImageButton) findViewById(R.id.getpath);
        Rmap = (LinearLayout) findViewById(R.id.map_container);
        Rcontent = (LinearLayout) findViewById(R.id.content1);
        mdatabase = FirebaseDatabase.getInstance("https://dev-splicer-300709-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //ViewTreeObserver observer = observer = Rmap.getViewTreeObserver();
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.user_list_map);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
        getlocation();
        if (geoApiContext == null) {
            geoApiContext = new GeoApiContext.Builder().apiKey(MAPVIEW_BUNDLE_KEY).build();

        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params2 = Rcontent.getLayoutParams();
                if (i == 0) {
                    params2.height = 0;
                    Rcontent.setLayoutParams(params2);
                    i = 1;
                } else {
                    params2.height = 500;
                    Rcontent.setLayoutParams(params2);
                    i = 0;
                }

            }
        });
        imageButtonPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display_track(latutude, longitude, lat_des, long_des);
            }
        });



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        lat_des = 35.5024538;
        long_des = -5.959056;



        LatLng position = new LatLng(lat_des, long_des);
        MarkerOptions markerOptions = new MarkerOptions().position(position).title("tach");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location2));
        map.addMarker(markerOptions);
        for(int i = 2;i<3 ;i++){

            int finalI = i;
            mdatabase.child("user_"+i).child("Profile").child("Last Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue().toString().equals("dfkjnvj")){

                        System.out.println("hello2");
                        mdatabase.child("user_"+i).child("Mission").child("Current Position").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                position_Emp= snapshot.getValue().toString();
                                System.out.println(position_Emp + "khacha");
                                System.out.println(snapshot + "hello");
                                String[] position_Emp1 = position_Emp.split(",");
                                employee_lat =  Double.parseDouble(position_Emp1[0]);
                                employee_lon =  Double.parseDouble(position_Emp1[1]);
                                LatLng position_emp = new LatLng(employee_lat, employee_lon);
                                MarkerOptions markerOptions1 = new MarkerOptions().position(position_emp).title("Hi my name is soufian");
                                map.addMarker(markerOptions1);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            break;

        }





    }

    public void Display_track(double lat, double lon, double lat_d, double lon_d) {

        try {
            Uri uri = Uri.parse("http://www.google.co.in/maps/dir/" + Double.toString(lat) + "," + Double.toString(lon) + "/" + Double.toString(lat_d) + "," + Double.toString(lon_d));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=fr&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            System.out.println(e);
        }
    }

    public void getlocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //whenn seccus
                    if (location != null) {
                        mMapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                // initialiser latt longetude
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                latutude = location.getLatitude();
                                longitude = location.getLongitude();
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                googleMap.setMyLocationEnabled(true);






                            }
                        });
                    }
                }
            });
        } else {
            // when permition denieid
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

    }






    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }





}