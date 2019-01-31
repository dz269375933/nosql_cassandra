package com.bjtu.dz.bean;

import java.util.Map;

public class JSONClass {
    private Map<String,String> _id;
    private String name;
    private String gender;
    private int age;
    private String occupation;
    private Movie movie;


    public Map<String, String> get_id() {
        return _id;
    }

    public void set_id(Map<String, String> _id) {
        this._id = _id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
