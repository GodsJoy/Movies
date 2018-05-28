package com.example.android.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    //used as tag for extra in intent
    public static final String EXTRA_POSITION = "extra_position";

    //Views to be populated in activity_details
    private TextView mTitleTV;
    private TextView mReleaseDateTV;
    private ImageView mPosterIV;
    private TextView mVoteAVGTV;
    private TextView mSynopsisTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTV = (TextView) findViewById(R.id.titleTV);
        mReleaseDateTV = (TextView) findViewById(R.id.releaseDateTV);
        mPosterIV = (ImageView) findViewById(R.id.posterIV);
        mVoteAVGTV = (TextView) findViewById(R.id.voteAVGTV);
        mSynopsisTV = (TextView) findViewById(R.id.synopsisTV);

        //get intent used to launch this class
        //Reference : SandWich project in this Phase
        Intent intent = getIntent();
        if(intent == null){
            //close app if intent is null
            closeOnError();
        }

        //get extra data from intent which is a Movie object
        Movie m = intent.getParcelableExtra(EXTRA_POSITION);
        if(m == null){
            //close app if object is null
            closeOnError();
            return;
        }
        //populate activity_details using Movie data
        //Reference : SandWich project in this Phase
        populateDetails(m);
        setTitle(getString(R.string.details_name));
    }

    //method to populate UI
    private void populateDetails(Movie m){
        mTitleTV.setText(m.getOriginalTitle());
        mReleaseDateTV.setText(m.getReleaseDate());
        Picasso.with(this).load(Movie.IMAGE_PATH.concat(m.getposterPath())).into(mPosterIV);
        mVoteAVGTV.setText(m.getRating()+"");
        mSynopsisTV.setText(m.getSynopsis());
    }

    //Reference : SandWich project in this Phase
    private void closeOnError(){
        finish();
        Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT);
    }
}
