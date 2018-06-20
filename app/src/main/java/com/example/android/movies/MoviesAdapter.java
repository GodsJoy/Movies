package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ayomide on 6/7/18.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private Movie [] allMovies;

    private final Context mContext;

    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MoviesAdapter(@NonNull Context context, MoviesAdapterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.movies_list;
        //Log.d("BeforeCast", "worked before cast");
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        //view.setFocusable(true);
        //Log.d("AfterCast", "still worked after cast");
        return new MoviesAdapterViewHolder(view);
    }


    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;
        public MoviesAdapterViewHolder(View view){

            super(view);
            //Log.d("InViewHolder", "got here");

            mImageView = (ImageView) view.findViewById(R.id.movieIV);
            view.setOnClickListener(this);
            //Log.d("InViewHolder", "got here2");

        }

        public void onClick(View v){
            /*Movie selectedMovie = allMovies[getAdapterPosition()];
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_POSITION, selectedMovie);
            startActivity(intent);*/
            int adapterPos = getAdapterPosition();
            mClickHandler.onClick(allMovies[adapterPos]);

        }
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder movieHolder, int position) {
        //let Picasso load image using complete image path
        movieHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(Movie.IMAGE_PATH.concat(allMovies[position].getposterPath())).into(movieHolder.mImageView);
        //set imageID with position to be used when image is clicked
        movieHolder.mImageView.setId(position);
        //Log.d("Bind", "got to bind3");

    }

    @Override
    public int getItemCount() {
        if(allMovies == null)
            return 0;
        else
            return allMovies.length;
    }

    public void setMoviesData(Movie [] moviesData){
        //Log.d("Notify", moviesData.length+"got here lenght");
        allMovies = moviesData;
        notifyDataSetChanged();
    }
}
