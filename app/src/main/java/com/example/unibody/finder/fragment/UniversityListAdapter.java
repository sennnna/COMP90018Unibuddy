package com.example.unibody.finder.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;

import java.util.HashMap;
import java.util.List;

public class UniversityListAdapter extends RecyclerView.Adapter<UniversityListAdapter.ListHolder> {
    private ImageView iv_university;
    private TextView tv_university;
    private TextView tv_distance;
    private List<University> sampleList;

    class ListHolder extends RecyclerView.ViewHolder{
        public ListHolder(@NonNull View itemView){
            super(itemView);
            // 获取控件
            iv_university = itemView.findViewById(R.id.img_university);
            tv_university = itemView.findViewById(R.id.finder_list_content_university);
            tv_distance   = itemView.findViewById(R.id.campus_distance);
        }

        public void setData(University university){
            tv_university.setText(university.getUniversityName());
            tv_distance.setText(university.getDistance());
            HashMap<String, Integer> universityImg = new HashMap<String,Integer>();
            universityImg.put("University of New South Wales", R.drawable.wales);
            universityImg.put("The University of Queensland", R.drawable.queensland);
            universityImg.put("The University of Melbourne", R.drawable.melbourne);
            universityImg.put("The Australian National University", R.drawable.australian);
            universityImg.put("Monash University", R.drawable.monash);
            universityImg.put("The University of Sydney", R.drawable.sydney);
            universityImg.put("The University of Adelaide", R.drawable.adelaide);
            universityImg.put("The University of Western Australia", R.drawable.western_australia);
            universityImg.put("Murdoch University", R.drawable.murdoch);
            universityImg.put("Melbourne Polytechnic", R.drawable.melbourne_polytechnic);
            universityImg.put("Macquarie University", R.drawable.macquarie);
            universityImg.put("Victoria University", R.drawable.victoria);
            iv_university.setImageResource(universityImg.get(university.getUniversityName()));



        }
    }

    public void setSampleList(List<University> sampleList) {
        this.sampleList = sampleList;
    }

    // 构造函数，传入要展示的数据
    public UniversityListAdapter(List<University> sampleList){
        this.sampleList = sampleList;
    }


    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sampleView = View.inflate(parent.getContext(), R.layout.finder_campus_list,null);
        ListHolder listHolder = new ListHolder(sampleView);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.setData(sampleList.get(position));
    }

    @Override
    public int getItemCount() {
        if(sampleList != null){
            return sampleList.size();
        }
        return 0;
    }

}
