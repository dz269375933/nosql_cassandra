package com.bjtu.dz.bean;

public class MovieTemp {
    int movieId;
    String movieTitle;
    int movieRating;
    String userName;
    String gender;
    String occupation;
    int age;
    public MovieTemp(){}
    public MovieTemp(JSONClass object){
        this.movieId=object.getMovie().getId();
        this.movieTitle=object.getMovie().getTitle();
        this.movieRating=object.getMovie().getRating();
        this.userName=object.getName();
        this.gender=object.getGender();
        this.occupation=object.getOccupation();
        this.age=object.getAge();
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(int movieRating) {
        this.movieRating = movieRating;
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
