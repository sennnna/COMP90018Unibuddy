package com.example.unibody.finder.fragment;

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

import java.util.ArrayList;
import java.util.List;

public class FinderFragment extends Fragment {

    BottomNavigationView TopNavigationView;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location UserLocation;
    List<Student> students = new ArrayList<>();

    String[][] users = {{"Jack", "Male", "-37.797114", "144.958450", "The University of Melbourne", "Single", "300m"},
            {"John", "Male", "-37.794150", "144.963522", "The University of Melbourne", "Single", "350m"},
            {"Lucy", "Female", "-37.796028", "144.967168", "The University of Melbourne", "Single", "4800m"},
            {"Tiffany", "Female", "-37.802353", "144.967247", "The University of Melbourne", "Single", "500m"},
            {"Katerina", "Female", "-37.803272", "144.957023", "The University of Melbourne", "Single", "800m"}};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finder, container, false);

        students.add(new Student("Nick", "IT", "male", "The University of Melbourne", "single", "1.1km", R.mipmap.student1, -37.800512, 144.959430));
        students.add(new Student("Jack", "Music", "male", "The University of Melbourne", "dating", "1.2km", R.mipmap.student2, -37.799392, 144.963061));
        students.add(new Student("Tom", "IT", "male", "The University of Melbourne", "dating", "2km", R.mipmap.student3, -37.797783, 144.957777));
        students.add(new Student("Anna", "Music", "female", "The University of Melbourne", "dating", "2km", R.mipmap.student4, -37.796080, 144.963179));
        students.add(new Student("Steven", "IT", "male", "The University of Melbourne", "secret", "2.2km", R.mipmap.student5, -37.792022, 144.960375));
        students.add(new Student("Tina", "Music", "female", "The University of Melbourne", "dating", "3km", R.mipmap.student6, -37.791275, 144.955003));
        students.add(new Student("Amelia", "Math", "female", "The University of Melbourne", "single", "3km", R.mipmap.student7, -37.791159, 144.967370));
        students.add(new Student("Hellen", "Math", "female", "The University of Melbourne", "single", "3km", R.mipmap.student8, -37.799042, 144.956656));
        students.add(new Student("Olive", "IT", "female", "The University of Melbourne", "secret", "3km", R.mipmap.student9, -37.800908, 144.968314));
        students.add(new Student("Susan", "Engineering", "female", "The University of Melbourne", "dating", "3km", R.mipmap.student10, -37.795730, 144.965717));
        students.add(new Student("Vivian", "Arts", "female", "Monash University", "dating", "25km", R.mipmap.student11, -37.797114, 144.958450));
        students.add(new Student("William", "Math", "male", "Monash University", "single", "25km", R.mipmap.student12, -37.794150, 144.963522));
        students.add(new Student("Cathy", "Music", "female", "Monash University", "secret", "26km", R.mipmap.student13, -37.796028, 144.967168));
        students.add(new Student("Flower", "Arts", "male", "Monash University", "dating", "27km", R.mipmap.student14, -37.802353, 144.967247));
        students.add(new Student("Agatha", "Arts", "male", "Monash University", "single", "28km", R.mipmap.student15,  -37.803272, 144.957023));

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
                for (int i = 0; i < students.size(); i++)
                {
                    //可以切一下输入法吗
                    if(students.get(i).getSex().equalsIgnoreCase(Filter.GENDER)
                        && students.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)){
                        createMarker(googleMap, students.get(i));
                    }
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        String name = marker.getTitle();
                        for (int i = 0; i < students.size(); i++)
                        {
                            if (students.get(i).getName().equals(name))
                            {
                                Intent intent = new Intent(getActivity(), FinderProfileActivity.class);
                                intent.putExtra("student", students.get(i));
                                startActivity(intent);
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void createMarker(GoogleMap googleMap, Student student){
        float color;
        if (student.getSex() == "male")
        {
            color = BitmapDescriptorFactory.HUE_BLUE;
        }
        else
        {
            color = BitmapDescriptorFactory.HUE_ROSE;
        }
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(student.getLat(), student.getLon()))
                .title(student.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }
}