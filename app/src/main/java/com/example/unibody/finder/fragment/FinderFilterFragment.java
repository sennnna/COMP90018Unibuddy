package com.example.unibody.finder.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unibody.R;

public class FinderFilterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.finder_filter_fragment, container, false);

        getActivity().findViewById(R.id.finder_top_navigator).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.INVISIBLE);

        return view;
    }
}