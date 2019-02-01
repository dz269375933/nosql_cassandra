package com.bjtu.dz.bean;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(keyspace = "movieRating", name = "userMovieRating")
public class UserMovieRating {
    @PartitionKey
    String userName;

    String gender;
    int age;
    String occupation;

    @Column(name = "movie")
    List<MovieType> movieList;

    public UserMovieRating(JSONClass object){
        this.setAge(object.getAge());
        this.setGender(object.getGender());
        this.setMovieList(new MovieType(object.getMovie()));
        this.setOccupation(object.getOccupation());
        this.setUserName(object.getName());
    }

    private void setMovieList(MovieType movieType) {
        movieList=new ArrayList<MovieType>();
        movieList.add(movieType);
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


}
