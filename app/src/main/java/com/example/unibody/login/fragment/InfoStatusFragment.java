package com.example.unibody.login.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unibody.R;

public class InfoStatusFragment extends Fragment {

    private CardView dating_card,single_card,unknown_card;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_status,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        dating_card = view.findViewById(R.id.dating_card);
        single_card = view.findViewById(R.id.single_card);
        unknown_card = view.findViewById(R.id.unknown_card);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dating_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                unknown_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                dating_card.setCardBackgroundColor(getResources().getColor(R.color.black));
            }
        });
        single_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dating_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                unknown_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                single_card.setCardBackgroundColor(getResources().getColor(R.color.black));
            }
        });
        unknown_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dating_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                single_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                unknown_card.setCardBackgroundColor(getResources().getColor(R.color.black));
            }
        });
    }
}