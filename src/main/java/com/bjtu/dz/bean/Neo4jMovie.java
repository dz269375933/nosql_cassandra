package com.bjtu.dz.bean;

public class Neo4jMovie {
    int movieId;
    String movieTitle;

    public Neo4jMovie(){}
    public Neo4jMovie(JSONClass object){
        this.movieId=object.getMovie().getId();
        this.movieTitle=object.getMovie().getTitle();
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

}
