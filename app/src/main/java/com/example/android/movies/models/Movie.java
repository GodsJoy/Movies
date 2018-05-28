package com.example.android.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayomide on 5/19/18.
 */

public class Movie implements Parcelable{
    //actual tags used in json result returned from TMDB server
    public final static String ORIGINAL_TITLE = "original_title";
    public final static String POSTER_PATH = "poster_path";
    public final static String OVERVIEW = "overview";
    public final static String RELEASE_DATE = "release_date";
    public final static String VOTE_AVERAGE = "vote_average";
    public final static String IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";

    //movie attributes
    private String originalTitle = "";
    private String posterPath = "";
    private String synopsis = "";
    private float rating = 0;
    private String releaseDate = null;

    //constructor of class Movie
    public Movie(String originalTitle,
                 String posterPath,
                 String synopsis,
                 float rating,
                 String date){
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = date;
    }

    //Used to create a Parcel for use as intent Extra in DetailActivity
    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterPath = in.readString();
        synopsis = in.readString();
        rating = in.readFloat();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //getter and setter methods
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getposterPath() {
        return posterPath;
    }

    public void setposterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(synopsis);
        parcel.writeFloat(rating);
        parcel.writeString(releaseDate);
    }
}
