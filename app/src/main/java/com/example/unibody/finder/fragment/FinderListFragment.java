package com.example.unibody.finder.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.unibody.R;

public class FinderListFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.finder_list_fragment, container, false);

        Button map_view = view.findViewById(R.id.map_view);
        map_view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.map_view:
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFragment()).commit();
        }
    }
}