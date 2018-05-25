package com.example.android.movies.models;

import android.media.Image;

import java.sql.Date;

/**
 * Created by ayomide on 5/19/18.
 */

public class Movie {
    private String originalTitle = "";
    private String image = "";
    private String synopsis = "";
    private int rating = 0;
    private Date releaseDate = null;

    public Movie(){

    }

    public Movie(String originalTitle,
                 String image,
                 String synopsis,
                 int rating,
                 Date date){
        this.originalTitle = originalTitle;
        this.image = image;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = date;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
