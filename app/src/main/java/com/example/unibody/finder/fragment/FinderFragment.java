package com.example.unibody.finder.fragment;

import static java.lang.Float.parseFloat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.unibody.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FinderFragment extends Fragment {

    BottomNavigationView TopNavigationView;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location UserLocation;

    String[][] users = {{"Yi Cao", "Male", "-37.797114", "144.958450"},
            {"Zunjie Xu", "Male", "-37.794150", "144.963522"},
            {"Yuwei Gu", "Female", "-37.796028", "144.967168"},
            {"Gangdan Shu", "Female", "-37.802353", "144.967247"},
            {"Meng Yang", "Female", "-37.803272", "144.957023"}};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finder, container, false);

        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.VISIBLE);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        TopNavigationView = view.findViewById(R.id.finder_top_navigator);

        TopNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.campus_nav:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FinderCampusFragment()).commit();
                        return true;
                }
                return false;
            }
        });

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        }
        else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        Button list_view = view.findViewById(R.id.list_view);
        list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.list_view) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderListFragment()).commit();
                }
            }
        });

        ImageButton filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.filter) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFilterFragment()).commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        }
        else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task)
                {
                    UserLocation = task.getResult();

                    if (UserLocation != null) {
                        setUserMarker(UserLocation);
                    }
                    else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000).setFastestInterval(1000).setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult)
                            {
                                UserLocation = locationResult.getLastLocation();
                                setUserMarker(UserLocation);
                            }
                        };

                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    }
                }
            });
        }
        else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void setUserMarker (Location location){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.clear();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions options = new MarkerOptions().position(latLng).title("Me");
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(options);
                for (int i = 0; i < users.length; i++)
                {
                    createMarker(googleMap, users[i]);
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        String name = marker.getTitle();
                        for (int i = 0; i < users.length; i++)
                        {
                            if (users[i][0].equals(name))
                            {
                                Bundle bundle = new Bundle();
                                String[] userInfo = users[i];
                                bundle.putStringArray("user", userInfo);
                                UserFragment userFragment = new UserFragment();
                                userFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.fragment, userFragment).commit();
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void createMarker(GoogleMap googleMap, String[] user){
        float color;
        if (user[1] == "Male")
        {
            color = BitmapDescriptorFactory.HUE_BLUE;
        }
        else
        {
            color = BitmapDescriptorFactory.HUE_ROSE;
        }
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(parseFloat(user[2]), parseFloat(user[3])))
                .title(user[0])
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }
}