package com.example.unibody.finder.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取空间
        RadioGroup genderGroup = getActivity().findViewById(R.id.finder_filter_chooseGender);
        RadioGroup stateGroup = getActivity().findViewById(R.id.finder_filter_chooseStates);
        Spinner ageSpinner = getActivity().findViewById(R.id.finder_filter_Spinner_age);
        Spinner universitySpinner = getActivity().findViewById(R.id.finder_filter_Spinner_university);
        Spinner distanceSpinner =  getActivity().findViewById(R.id.finder_filter_Spinner_distance);


        getActivity().findViewById(R.id.finder_filter_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"Hello",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFragment()).commit();

            }
        });
        getActivity().findViewById(R.id.finder_filter_save).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                RadioButton genderCheckedBtn = getActivity().findViewById(genderGroup.getCheckedRadioButtonId());
                RadioButton stateCheckedBtn = getActivity().findViewById(stateGroup.getCheckedRadioButtonId());
                String genderStr = genderCheckedBtn.getText().toString();
                String status = stateCheckedBtn.getText().toString();
                String age = ageSpinner.getSelectedItem().toString();
                String university = universitySpinner.getSelectedItem().toString();
                String distance = distanceSpinner.getSelectedItem().toString();

                FinderListFragment finderListFragment = FinderListFragment.newInstance(genderStr,status,age,university,distance);
                getFragmentManager().beginTransaction().replace(R.id.fragment, finderListFragment).commit();
            }
        });

        /**
         * 这里，我刚刚加的这两个，先不要直接就用Filter。GENDER这类的，你先在这个类里面创建一个变量存储
         * 因为我刚刚看到了有一个保存按钮
         * 我给你演示一个
         */
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.finder_filter_BoyButtonId:
                        Filter.GENDER = "male";
                        break;
                    case R.id.finder_filter_GirlButtonId:
                        Filter.GENDER = "female";
                        break;
                }
            }
        });
        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.finder_filter_SingleButtonId:
                        Filter.STATUS = "single";
                        break;
                    case R.id.finder_filter_DatingButtonId:
                        Filter.STATUS = "dating";
                        break;
                    case R.id.finder_filter_SecretButtonId:
                        Filter.STATUS = "secret";
                }
            }
        });
    }
}
