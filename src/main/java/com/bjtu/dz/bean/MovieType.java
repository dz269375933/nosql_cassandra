package com.bjtu.dz.bean;

import com.bjtu.dz.bean.Movie;
import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "movieRating", name = "movieType")
public class MovieType {
    int movie_id;
    int user_rating;
    String movie_title;

    public MovieType(Movie movie){
        this.movie_id=movie.getId();
        this.user_rating=movie.getRating();
        this.movie_title=movie.getTitle();
    }
    public MovieType(){}
    public MovieType(int movie_id,int user_rating,String movie_title){
        this.movie_id=movie_id;
        this.user_rating=user_rating;
        this.movie_title=movie_title;
    }
    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }





}
