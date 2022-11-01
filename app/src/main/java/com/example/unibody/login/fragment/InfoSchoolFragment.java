package com.example.unibody.login.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.unibody.R;
import com.example.unibody.login.viewmodel.InfoSchoolViewModel;

import java.util.List;


public class InfoSchoolFragment extends Fragment {

    private InfoSchoolViewModel infoSchoolViewModel;

    private NumberPicker school_picker;
    private EditText search_school_edit;
    private TextView tips_tv;
    private String school = "Harvard University";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_school,container,false);
        initView(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void initView(View view){
        school_picker = view.findViewById(R.id.school_picker);
        search_school_edit = view.findViewById(R.id.search_school_edit);
        tips_tv = view.findViewById(R.id.tips_tv);

        school_picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        school_picker.setSelectionDividerHeight(0);
        school_picker.setMinValue(0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        infoSchoolViewModel = new ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(InfoSchoolViewModel.class);
        infoSchoolViewModel.loadSchool();

        infoSchoolViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings.isEmpty()){
                    school_picker.setVisibility(View.GONE);
                    tips_tv.setVisibility(View.VISIBLE);
                    return;
                }
                school_picker.setVisibility(View.VISIBLE);
                tips_tv.setVisibility(View.GONE);
                String[] schools = new String[strings.size()];
                for (int i = 0; i < strings.size(); i++) {
                    schools[i] = strings.get(i);
                }
                school = schools[0];
                System.out.println(strings.size());
                System.out.println(schools.length);
                if (school_picker.getMaxValue() > schools.length - 1){
                    school_picker.setMaxValue(schools.length - 1);
                    school_picker.setDisplayedValues(schools);
                }else {
                    school_picker.setDisplayedValues(schools);
                    school_picker.setMaxValue(schools.length - 1);
                }
                school_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        school = schools[newVal];
                    }
                });
            }
        });

        search_school_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                infoSchoolViewModel.searchSchool(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public String getSchool() {
        return school;
    }
}
