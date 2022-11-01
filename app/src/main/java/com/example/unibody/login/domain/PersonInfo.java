package com.example.unibody.login.domain;

import android.net.Uri;

public class PersonInfo {

    private String phoneNumber;
    private Uri headUri;
    private String name;
    private String gender;
    private String height;
    private String birth;
    private String school;
    private String status;
    private String quiz1 = "";
    private String quiz2 = "";

    public PersonInfo(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getHeadUri() {
        return headUri;
    }

    public void setHeadUri(Uri headUri) {
        this.headUri = headUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuiz1() {
        return quiz1;
    }

    public void setQuiz1(String quiz1) {
        if (quiz1 == null){
            return;
        }
        this.quiz1 = quiz1;
    }

    public String getQuiz2() {
        return quiz2;
    }

    public void setQuiz2(String quiz2) {
        if (quiz2 == null){
            return;
        }
        this.quiz2 = quiz2;
    }
}
