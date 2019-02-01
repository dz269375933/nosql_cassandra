package com.bjtu.dz.bean;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "movieRating", name = "userType")
public class User {
    String userName;
    int user_rating;
    String gender;
    String occupation;
    int age;

    public User(MovieTemp object){
        this.userName=object.getUserName();
        this.user_rating=object.getMovieRating();
        this.gender=object.getGender();
        this.occupation=object.getOccupation();
        this.age=object.getAge();
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
