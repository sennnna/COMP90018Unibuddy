package com.example.unibody.finder.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 在哪个类
 *
 */

public class FinderListFragment extends Fragment implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView TopNavigationView;
    RecyclerView studentList;
    FinderProfileFragment profileFragment;

    public static FinderListFragment newInstance(String gender, String status, String age, String university, String distance) {
        FinderListFragment finder = new FinderListFragment();
        Bundle args = new Bundle();
        args.putString("gender", gender);
        args.putString("age", age);
        args.putString("status", status);
        args.putString("university", university);
        args.putString("distance", distance);
        finder.setArguments(args);
        return finder;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.finder_list_fragment, container, false);

        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.VISIBLE);
        Button map_view = view.findViewById(R.id.map_view);
        map_view.setOnClickListener(this);

        List<Student> students = new ArrayList<>();
        students.add(new Student("Nick", "IT", "male", "The University of Melbourne", "single", "1.1km", R.mipmap.student1, -37.800512, 144.959430));
        students.add(new Student("Jack", "Music", "male", "The University of Melbourne", "dating", "1.2km", R.mipmap.student2, -37.799392, 144.963061));
        students.add(new Student("Tom", "IT", "male", "The University of Melbourne", "dating", "2km", R.mipmap.student3, -37.797783, 144.957777));
        students.add(new Student("Anna", "Music", "female", "The University of Melbourne", "dating", "2km", R.mipmap.student4, -37.796080, 144.963179));
        students.add(new Student("Steven", "IT", "male", "The University of Melbourne", "secret", "2.2km", R.mipmap.student5, -37.792022, 144.960375));
        students.add(new Student("Tina", "Music", "female", "The University of Melbourne", "dating", "3km", R.mipmap.student6, -37.791275, 144.955003));
        students.add(new Student("Amelia", "Math", "female", "The University of Melbourne", "single", "3km", R.mipmap.student7, -37.791159, 144.967370));
        students.add(new Student("Hellen", "Math", "female", "The University of Melbourne", "single", "3km", R.mipmap.student8, -37.799042, 144.956656));
        students.add(new Student("Olive", "IT", "female", "The University of Melbourne", "secret", "3km", R.mipmap.student9, -37.800908, 144.968314));
        students.add(new Student("Susan", "Engineering", "female", "The University of Melbourne", "dating", "3km", R.mipmap.student10, -37.795730, 144.965717));
        students.add(new Student("Vivian", "Arts", "female", "Monash University", "dating", "25km", R.mipmap.student11, -37.797114, 144.958450));
        students.add(new Student("William", "Math", "male", "Monash University", "single", "25km", R.mipmap.student12, -37.794150, 144.963522));
        students.add(new Student("Cathy", "Music", "female", "Monash University", "secret", "26km", R.mipmap.student13, -37.796028, 144.967168));
        students.add(new Student("Flower", "Arts", "male", "Monash University", "dating", "27km", R.mipmap.student14, -37.802353, 144.967247));
        students.add(new Student("Agatha", "Arts", "male", "Monash University", "single", "28km", R.mipmap.student15,  -37.803272, 144.957023));

        studentList = view.findViewById(R.id.list_info);
        studentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudentListAdapter studentListAdapter = new StudentListAdapter(getActivity(), students, new StudentListAdapter.OnItemClickListaner() {
            @Override
            public void onItemCLicked(int position) {

                Intent intent = new Intent(getActivity(), FinderProfileActivity.class);
                intent.putExtra("student", students.get(position));
                startActivity(intent);

            }
        });
        studentList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        studentList.setAdapter(studentListAdapter);
//            getDataFromServer();
        TopNavigationView = view.findViewById(R.id.finder_top_navigator);
        TopNavigationView.setOnNavigationItemSelectedListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_view:
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFragment()).commit();
        }
    }


    public void getDataFromServer() {
        // 自动生成一批数据
        List<Student> students = new ArrayList<>();
        String url = "http://3.26.21.18/api/v1/user/getUserByFilter";
        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("gender",gender);
//            jsonObject.put("status",status);
//            jsonObject.put("age","");
//            jsonObject.put("university",university);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json; charset=utf-8");
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_TEXT, jsonObject.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("XXXXXXXXXXXXXXXX", "onFailure" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("YYYYYYYYYYYYYYYYY", "onResponse: " + result);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        students.add(new Student(jsonObject.getString("username"),
                                "game",
                                jsonObject.getString("gender"),
                                jsonObject.getString("university"),
                                jsonObject.getString("status"),
                                "",
                                R.mipmap.student1,
                                jsonObject.getDouble("lat"),
                                jsonObject.getDouble("lon")));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            StudentListAdapter studentListAdapter = new StudentListAdapter(students);
//                            studentList.setAdapter(studentListAdapter);
//                            studentListAdapter.setOnItemClickListener(new StudentListAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(int position) {
//                                    Toast.makeText(getActivity(),"您点击了"+position+"行",Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                    });
                } catch (JSONException e) {
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.student_nav:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FinderCampusFragment()).commit();
                return true;
        }
        return false;
    }
}