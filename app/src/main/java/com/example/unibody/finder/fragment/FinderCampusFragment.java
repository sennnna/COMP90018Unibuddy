package com.example.unibody.finder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FinderCampusFragment extends Fragment {

    BottomNavigationView TopNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.finder_campus_fragment, container, false);
        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.VISIBLE);
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

        // 获取搜索框
        SearchView searchView = view.findViewById(R.id.search_campus);
        RecyclerView campusList = view.findViewById(R.id.finder_campus_list);
        UniversityListAdapter universityListAdapter = new UniversityListAdapter(getCampusData(false,""));
        LinearLayoutManager layoutManager = new LinearLayoutManager(null);
        campusList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        campusList.setAdapter(universityListAdapter);
        campusList.setLayoutManager(layoutManager);

        //搜索框监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //输入完成后，提交时触发的方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            //在输入时触发的方法，当字符真正显示到searchView中才触发
            @Override
            public boolean onQueryTextChange(String newText) {
                universityListAdapter.setSampleList(getCampusData(true,newText));
                campusList.setAdapter(universityListAdapter);
                return false;
            }
        });
        return view;
    }

    public List<University> getCampusData(Boolean isSearch, String searchStr){
        String[] campusName = {"The University of Melbourne","Monash University","University of New South Wales","The University of Queensland",
                                "The Australian National University","The University of Sydney",
                                "The University of Adelaide","The University of Western Australia","Murdoch University",
                                "Melbourne Polytechnic","Macquarie University","Victoria University"};
        String[] distance = {"0km","25km","689km","1350km","3300km","898km","730km","3000km","344km","10km","900km","30km"};

        List<University> campus = new ArrayList<University>();

        for(int i = 0;i <campusName.length;i++){
            if((isSearch && campusName[i].contains(searchStr)) || !isSearch) {
                campus.add(new University(campusName[i], distance[i]));
            }
        }
        return campus;
    }


}