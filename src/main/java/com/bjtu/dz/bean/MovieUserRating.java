package com.bjtu.dz.bean;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(keyspace = "movieRating", name = "movieUserRating")
public class MovieUserRating {
    @PartitionKey
    int movie_id;

    @Column(name = "movie_title")
    String movie_title;

    @Column(name = "user")
    List<User> userList;

    public MovieUserRating(MovieTemp movieTemp){
        this.movie_id=movieTemp.getMovieId();
        this.movie_title=movieTemp.getMovieTitle();
        userList=new ArrayList<User>();
        userList.add(new User(movieTemp));
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
