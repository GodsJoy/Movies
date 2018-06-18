package com.example.android.movies;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.content.Loader;

import java.net.URL;
import com.example.android.movies.utility.NetworkUtil;
import com.example.android.movies.utility.CreateMoviesFromResponseUtil;
import com.example.android.movies.models.Movie;


public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        MoviesAdapter.MoviesAdapterOnClickHandler,
        LoaderCallbacks<Movie[]>{

    private Movie [] mMovies; //array of movies processed from TMDB result
    private TextView sortText;
    private TextView errorText;
    private Spinner spinner;

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMovieAdapter;

    private static final int MOVIE_LOADER_ID = 0;


    //needed to prevent requesting result from TMDB by the spinner on launch of app
    private boolean sortOptionSelected = false;

    //store currently selected sort order to prevent requesting result from TMDB when the same order is selected
    private String currentlySelectedSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentlySelectedSortOrder = getResources().getStringArray(R.array.sort_options)[0];
        //spinner = (Spinner) findViewById(R.id.sort_spinner);
        //sortText = (TextView) findViewById(R.id.sort_options);
        errorText = (TextView) findViewById(R.id.errorTV);

        //spinner.setVisibility(View.INVISIBLE);
        //sortText.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.INVISIBLE);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MoviesAdapter(this, this);

        mRecyclerView.setAdapter(mMovieAdapter);

        LoaderCallbacks<Movie[]> callback = MainActivity.this;

        Bundle loaderBundle = new Bundle();
        loaderBundle.putCharSequence("sort_order", getString(R.string.popular));

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, loaderBundle, callback);

        //initial sort order is popular
        //searchMovies(getString(R.string.popular));

        //populate Spinner. It allows a user to change sort order
        //populateSpinner();

    }

    private void searchMovies(String sortOption){

        //build url needed to request data from TMDB

        Bundle loaderBundle = new Bundle();
        loaderBundle.putCharSequence("sort_order", sortOption);
        //process request in a background thread
        //new MoviesSearchTask().execute(movies);
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, loaderBundle, this);

    }

    /*//Reference : Sunshine app Grow with Google Phase 1
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

                spinner.setVisibility(View.VISIBLE);
                sortText.setVisibility(View.VISIBLE);

                //create a grid view to display poster images
                //Reference : https://developer.android.com/guide/topics/ui/layout/gridview
                GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new ImageAdapter(MainActivity.this, mMovies));

                //click listener launches DetailsActivity when an image is selected
                *//*Reference : https://developer.android.com/guide/topics/ui/layout/gridview,
                * Sunshine app Grow with Google Phase 1*//*
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
            else{
                errorText.setVisibility(View.VISIBLE);
            }

        }
    }
*/
    @Override
    public Loader<Movie[]> onCreateLoader(int id, final Bundle loaderArgs){
        return new AsyncTaskLoader<Movie[]>(this) {

            Movie [] mMovies = null;

            @Override
            protected void onStartLoading() {
                if(mMovies != null){
                    deliverResult(mMovies);
                }
                else{
                    errorText.setVisibility(View.VISIBLE);
                    forceLoad();
                }
                //super.onStartLoading();
            }

            @Override
            public Movie[] loadInBackground() {

                URL movies = NetworkUtil.buildUrl(loaderArgs.getString("sort_order"));
                //Log.d("loadINBackground", movies+" here1");

                try{
                    String result = NetworkUtil.getResponseFromHttpUrl(movies);
                    //return processed array of Movies
                    //Log.d("loadINBackground", result+"here");
                    return CreateMoviesFromResponseUtil.getEachMovie(result);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.d("loadINBackground", "failed to load data");
                    return null;
                }


            }

            public void deliverResult(Movie [] movies){
                mMovies = movies;
                super.deliverResult(movies);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        errorText.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMoviesData(data);
        if(data == null){
            showErrorMessage();
        }
        else{
            showMovieDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }

    //method to populate the spinner
    /*Reference : https://developer.android.com/guide/topics/ui/controls/spinner,
    * https://www.tutorialspoint.com/android/android_spinner_control.htm,
    * https://developer.android.com/guide/topics/ui/controls/spinner*/
    private void populateSpinner(){

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

    @Override
    public void onClick(Movie clickedMovie){
        //Movie selectedMovie = allMovies[getAdapterPosition()];
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_POSITION, clickedMovie);
        startActivity(intent);
    }

    private void showMovieDataView(){
        errorText.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);

        errorText.setVisibility(View.VISIBLE);
    }

    //it does nothing :)
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
