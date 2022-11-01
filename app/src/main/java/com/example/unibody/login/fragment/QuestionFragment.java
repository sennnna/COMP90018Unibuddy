package com.example.unibody.login.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.unibody.R;

public class QuestionFragment extends Fragment {


   
    private EditText quiz1_edit;
    private EditText quiz2_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        quiz1_edit = view.findViewById(R.id.quiz1_edit);
        quiz2_edit = view.findViewById(R.id.quiz2_edit);
    }
    
    public String quiz1(){
        return quiz1_edit.getText().toString();
    }

    public String quiz2(){
        return quiz2_edit.getText().toString();
    }
}
