package com.example.unibody.finder.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unibody.R;
import com.example.unibody.databinding.ActivityFinderProfileBinding;

public class FinderProfileActivity extends AppCompatActivity {

    ActivityFinderProfileBinding binding;

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinderProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        student = (Student) getIntent().getSerializableExtra("student");

        binding.finderProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.finderProfilePhoto.setImageResource(student.getHeadImg());

        binding.finderProfileDistance.setText(student.getDistance());
        binding.finderProfileName.setText(student.getName());
        binding.finderProfileUniversity.setText(student.getUniversity());
        binding.finderProfileStatus.setText(student.getStatus());
        binding.finderProfileMajor.setText(student.getMajor());

        if ("male".equals(student.getSex())) {
            binding.finderProfileGender.setImageResource(R.drawable.male);
        } else {
            binding.finderProfileGender.setImageResource(R.drawable.female);
        }
    }
}