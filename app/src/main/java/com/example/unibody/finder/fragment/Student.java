package com.example.unibody.finder.fragment;

import java.io.Serializable;

public class Student implements Serializable {
    private String name, major, sex, university, status, distance;
    private int headImg;
    private double lat, lon;

    public Student(String name, String major, String sex, String university, String status, String distance, int headImg, double lat, double lon) {
        this.name = name;
        this.major = major;
        this.sex = sex;
        this.university = university;
        this.status = status;
        this.distance = distance;
        this.headImg = headImg;
        this.lat = lat;
        this.lon = lon;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getUniversity() {
        return university;
    }

    public String getStatus() {
        return status;
    }

    public String getDistance() {
        return distance;
    }

    public double getLat() {return lat;}

    public double getLon() {return lon;}
}
