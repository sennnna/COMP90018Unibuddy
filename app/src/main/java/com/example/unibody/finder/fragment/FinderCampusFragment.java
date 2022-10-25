package com.example.unibody.finder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.unibody.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FinderCampusFragment extends Fragment {

    BottomNavigationView TopNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.finder_campus_fragment, container, false);

        TopNavigationView = view.findViewById(R.id.finder_top_navigator);

        TopNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.student_nav:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFragment()).commit();
                        return true;
                }
                return false;
            }
        });

        return view;
    }
}