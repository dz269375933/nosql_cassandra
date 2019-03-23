package com.bjtu.dz.bean;

public class User {
    String userName;
    String gender;
    String occupation;
    int age;
    int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public User(){}
    public User(JSONClass object){
        this.userName=object.getName();
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
