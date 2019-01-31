package com.bjtu.dz.bean;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.HashMap;
import java.util.Map;

@Table(keyspace = "movieRating", name = "userMovieRating")
public class UserMovieRating {
    @PartitionKey
    String userName;

    String gender;
    int age;
    String occupation;

    @Column(name = "movie")
    Map<Integer,MovieType> movieMap;

    public UserMovieRating(JSONClass object){
        this.setAge(object.getAge());
        this.setGender(object.getGender());
        this.setMovieMap(new MovieType(object.getMovie()));
        this.setOccupation(object.getOccupation());
        this.setUserName(object.getName());
    }

    private void setMovieMap(MovieType movieType) {
        movieMap=new HashMap<Integer, MovieType>();
        movieMap.put(movieType.getMovie_id(),movieType);
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
