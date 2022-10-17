package com.example.unibody.login.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.unibody.R;

import java.util.Calendar;

public class PersonBasicInfoFragment extends Fragment {

    private NumberPicker height_picker,year_picker,month_picker,day_picker;
    private CardView male_card,female_card;

    private final int MAX_HEIGHT = 210;
    private final int MIN_HEIGHT = 100;
    private final int MIN_MONTH_DAY = 1;
    private final int MIN_YEAR = 1970;

    private int year;
    private int month;
    private int day;

    //当前时间
    private final Calendar CALENDAR = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_basic_info, container, false);
        initView(view);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void initView(View view){
        height_picker = view.findViewById(R.id.height_picker);
        year_picker = view.findViewById(R.id.year_picker);
        month_picker = view.findViewById(R.id.month_picker);
        day_picker = view.findViewById(R.id.day_picker);
        male_card = view.findViewById(R.id.male_card);
        female_card = view.findViewById(R.id.female_card);

        //init picker

        height_picker.setSelectionDividerHeight(0);
        year_picker.setSelectionDividerHeight(0);
        month_picker.setSelectionDividerHeight(0);
        day_picker.setSelectionDividerHeight(0);

        height_picker.setMaxValue(MAX_HEIGHT);
        height_picker.setMinValue(MIN_HEIGHT);

        year_picker.setMaxValue(CALENDAR.get(Calendar.YEAR));
        year_picker.setMinValue(MIN_YEAR);

        month_picker.setMaxValue(CALENDAR.get(Calendar.MONTH)+1);
        month_picker.setMinValue(MIN_MONTH_DAY);

        day_picker.setMinValue(MIN_MONTH_DAY);
        day_picker.setMaxValue(CALENDAR.get(Calendar.DAY_OF_MONTH));

        year_picker.setValue(year_picker.getMaxValue());
        month_picker.setValue(month_picker.getMaxValue());
        day_picker.setValue(day_picker.getMaxValue());

        year = year_picker.getValue();
        month = month_picker.getValue();
        day = day_picker.getValue();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        height_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        year_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
                setMonthPicker();
                setDayPicker();
            }
        });

        month_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month = newVal;
                setDayPicker();
            }
        });

        day_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = newVal;
            }
        });

        female_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                female_card.setCardBackgroundColor(getResources().getColor(R.color.black));
            }
        });
        male_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female_card.setCardBackgroundColor(getResources().getColor(R.color.white));
                male_card.setCardBackgroundColor(getResources().getColor(R.color.black));
            }
        });

    }

    private void setMonthPicker(){
        //判断是否为当前年,如果是更改月最大为当前月,如果不是,更改月为12
        if (year == CALENDAR.get(Calendar.YEAR)){
            //如果是更改月最大为当前月
            month_picker.setMaxValue(CALENDAR.get(Calendar.MONTH)+1);
        }else {
            //如果不是,更改月为12
            month_picker.setMaxValue(12);
        }
    }

    private void setDayPicker(){
        if (year == CALENDAR.get(Calendar.YEAR) && month == CALENDAR.get(Calendar.MONTH) + 1){
            day_picker.setMaxValue(CALENDAR.get(Calendar.DAY_OF_MONTH));
            return;
        }
        //判断当前月是否为大月,如果是更改日为31,如果为2月进行计算,如果是小月,则为30
        if ((month % 2 != 0 && month < 8)||(month % 2 == 0 && month > 7)){
            day_picker.setMaxValue(31);
        }else if (month == 2){
            //计算闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                day_picker.setMaxValue(29);
            }else {
                day_picker.setMaxValue(28);
            }
        }else {
            day_picker.setMaxValue(30);
        }
    }
}