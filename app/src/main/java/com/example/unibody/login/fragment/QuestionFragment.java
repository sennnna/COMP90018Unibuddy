package com.example.unibody.login.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.unibody.R;

public class QuestionFragment extends Fragment {


    private RadioGroup quiz_one_radio;
    private RadioGroup quiz_two_radio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        quiz_one_radio = view.findViewById(R.id.quiz_one_radio);
        quiz_two_radio = view.findViewById(R.id.quiz_two_radio);

        quiz_one_radio.check(R.id.quiz_one_eq);
        quiz_two_radio.check(R.id.quiz_two_eq);
    }
}