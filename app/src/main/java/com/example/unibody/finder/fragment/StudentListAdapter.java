package com.example.unibody.finder.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.databinding.FinderListStudentListBinding;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {
    FinderListStudentListBinding finderListStudentListBinding;

    private Context context;
    private List<Student> examBeens;


    private OnItemClickListaner onItemClickListaner;

    public StudentListAdapter(Context context, List<Student> examBeens, OnItemClickListaner onItemClickListaner) {
        this.context = context;
        this.examBeens = examBeens;
        this.onItemClickListaner = onItemClickListaner;
    }

    public interface OnItemClickListaner {
        void onItemCLicked(int position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        finderListStudentListBinding = FinderListStudentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        MyViewHolder holder = new MyViewHolder(finderListStudentListBinding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.finderListStudentListBinding.finderListContentDistance.setText(examBeens.get(position).getDistance());
        holder.finderListStudentListBinding.finderListContentName.setText(examBeens.get(position).getName());
        holder.finderListStudentListBinding.finderListContentUniversity.setText(examBeens.get(position).getUniversity());
        holder.finderListStudentListBinding.finderListContentStatus.setText(examBeens.get(position).getStatus());
        holder.finderListStudentListBinding.finderListContentPhoto.setImageResource(examBeens.get(position).getHeadImg());

        if (examBeens.get(position).getStatus() == "dating") {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.dating1);
        } else if (examBeens.get(position).getStatus() == "single") {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.single1);
        } else {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.secret);
        }

        if (examBeens.get(position).getSex() == "male") {
            holder.finderListStudentListBinding.finderListContentGender.setImageResource(R.drawable.man);
        } else {
            holder.finderListStudentListBinding.finderListContentGender.setImageResource(R.drawable.woman);
        }

        holder.finderListStudentListBinding.finderListContentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListaner.onItemCLicked(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return examBeens.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private FinderListStudentListBinding finderListStudentListBinding;

        public MyViewHolder(FinderListStudentListBinding finderListStudentListBinding) {
            super(finderListStudentListBinding.getRoot());
            this.finderListStudentListBinding = finderListStudentListBinding;
        }
    }

}
