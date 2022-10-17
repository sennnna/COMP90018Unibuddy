package com.example.unibody.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.unibody.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InfoSchoolViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> schoolList = new MutableLiveData<>();
    private MutableLiveData<List<String>> searchResults = new MutableLiveData<>();

    public InfoSchoolViewModel(@NonNull Application application) {
        super(application);
    }

    //ViewModel
    public void loadSchool(){
        InputStream inputStream = getApplication().getResources().openRawResource(R.raw.school);
        try {
            int read;
            String school = "";
            List<String> data = new ArrayList<>();
            while ((read = inputStream.read()) != -1){
                if ((char) read == '\r'){
                    data.add(school);
                    school = "";
                }else {
                    school += (char) read;
                }
            }
            schoolList.setValue(data);
            searchSchool(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:
     *  模糊搜索集合,当搜索内容发生更新时搜索,如果搜索为空,则为整个学校集合
     *  遍历学校集合,有关键字匹配每一个学校,匹配成功加入搜索结果集合,
     */
    public void searchSchool(String school){
        if (school == null || "".equals(school)){
            searchResults.setValue(schoolList.getValue());
            return;
        }
        List<String> results = new ArrayList<>();
        for (String s : schoolList.getValue()) {
            if (s.contains(school)){
                results.add(s);
            }
        }
        searchResults.setValue(results);
    }

    public MutableLiveData<List<String>> getSchoolList() {
        return schoolList;
    }

    public MutableLiveData<List<String>> getSearchResults() {
        return searchResults;
    }
}
