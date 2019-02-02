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

    @Column(name = "userName")
    String userName;
    @Column(name = "rating")
    int rating;


    public MovieUserRating(MovieTemp movieTemp){
        this.movie_id=movieTemp.getMovieId();
        this.movie_title=movieTemp.getMovieTitle();
        this.userName=movieTemp.getUserName();
        this.rating=movieTemp.getMovieRating();
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
