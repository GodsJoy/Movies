package com.example.android.movies.utility;

import android.app.LoaderManager;
import android.util.Log;

import com.example.android.movies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ayomide on 5/24/18.
 * This class creates an array of movies from the JSON result from TMDB
 */

public class CreateMoviesFromResponseUtil {

    public static Movie[] getEachMovie(String moviesJSONStr) {
        try {
            if(moviesJSONStr != null) {
                //crete a JSONObject from the raw JSON result
                JSONObject movies = new JSONObject(moviesJSONStr);

                //get results field which describes each movie in a page
                JSONArray moviesResult = movies.getJSONArray("results");

                //create an array of movie with lenght of movies in moviesResult
                Movie[] allMovies = new Movie[moviesResult.length()];

                //information needed to retrieve from moviesResult
                String originalTitle = "";
                String image_path = "";
                String synopsis = "";
                float rating = 0;
                String releaseDate = null;

                //loop through each item in moviesResult and extract movie details
                for (int i = 0; i < moviesResult.length(); i++) {
                    JSONObject amovie = moviesResult.getJSONObject(i);
                    originalTitle = amovie.getString(Movie.ORIGINAL_TITLE);
                    image_path = amovie.getString(Movie.POSTER_PATH);
                    synopsis = amovie.getString(Movie.OVERVIEW);
                    rating = Float.parseFloat(amovie.getString(Movie.VOTE_AVERAGE));
                    releaseDate = amovie.getString(Movie.RELEASE_DATE);

                    //create a movie object from information extracted
                    allMovies[i] = new Movie(originalTitle,
                            image_path,
                            synopsis,
                            rating,
                            releaseDate);

                }
                //return the array of movie created
                return allMovies;
            }
            return null;
        }
        catch (JSONException j){
            return null;
        }

    }

}
