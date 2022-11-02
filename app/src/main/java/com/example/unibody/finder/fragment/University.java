package com.example.unibody.finder.fragment;

public class University {
    private String universityName,distance;
    public University(String universityName, String distance){
        this.universityName = universityName;
        this.distance = distance;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getDistance() {
        return distance;
    }
}
