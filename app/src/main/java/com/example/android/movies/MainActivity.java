package com.example.android.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.net.URL;
import com.example.android.movies.utility.NetworkUtil;
import com.example.android.movies.utility.CreateMoviesFromResponseUtil;
import com.example.android.movies.models.Movie;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Movie [] mMovies; //array of movies processed from TMDB result

    //needed to prevent requesting result from TMDB by the spinner on launch of app
    private boolean sortOptionSelected = false;

    //store currently selected sort order to prevent requesting result from TMDB when the same order is selected
    private String currentlySelectedSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentlySelectedSortOrder = getResources().getStringArray(R.array.sort_options)[0];

        //initial sort order is popular
        searchMovies(getString(R.string.popular));

        //populate Spinner. It allows a user to change sort order
        populateSpinner();

    }

    private void searchMovies(String sortOption){

        //build url needed to request data from TMDB
        URL movies = NetworkUtil.buildUrl(sortOption);

        //process request in a background thread
        new MoviesSearchTask().execute(movies);

    }

    //Reference : Sunshine app Grow with Google Phase 1
    public class MoviesSearchTask extends AsyncTask<URL, Void, Movie []>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie [] doInBackground(URL... urls) {
            if(urls.length == 0){
                return null;
            }
            String result = NetworkUtil.getResponseFromHttpUrl(urls[0]);

            //return processed array of Movies
            return CreateMoviesFromResponseUtil.getEachMovie(result);
        }

        @Override
        protected void onPostExecute(Movie [] m) {
            if(m != null){

                mMovies = m; //set array of movies

                //create a grid view to display poster images
                //Reference : https://developer.android.com/guide/topics/ui/layout/gridview
                GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new ImageAdapter(MainActivity.this, mMovies));

                //click listener launches DetailsActivity when an image is selected
                /*Reference : https://developer.android.com/guide/topics/ui/layout/gridview,
                * Sunshine app Grow with Google Phase 1*/
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        Movie selectedMovie = mMovies[position];
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_POSITION, selectedMovie);
                        startActivity(intent);
                    }
                });
            }

        }
    }

    //method to populate the spinner
    /*Reference : https://developer.android.com/guide/topics/ui/controls/spinner,
    * https://www.tutorialspoint.com/android/android_spinner_control.htm,
    * https://developer.android.com/guide/topics/ui/controls/spinner*/
    private void populateSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //method that gets called when a Spinner item is selected
    /*Reference : https://developer.android.com/guide/topics/ui/controls/spinner,
    * https://www.tutorialspoint.com/android/android_spinner_control.htm,
    * https://developer.android.com/guide/topics/ui/controls/spinner*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedOption = (String) parent.getItemAtPosition(pos);
        Log.d("Spinner", selectedOption+" "+currentlySelectedSortOrder);

        if (sortOptionSelected) {
            if(!selectedOption.equals(currentlySelectedSortOrder)) {
                if (selectedOption.equals(getResources().getStringArray(R.array.sort_options)[0])) {
                    searchMovies(getString(R.string.popular));
                } else {
                    searchMovies(getString(R.string.top_rated));
                }
            }

        } else {
            sortOptionSelected = true;
        }
        currentlySelectedSortOrder = selectedOption;

    }

    //it does nothing :)
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
