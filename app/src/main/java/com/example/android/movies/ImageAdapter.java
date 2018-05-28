package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.android.movies.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by ayomide on 5/27/18.
 * This class creates an adapter for displaying images in GridView
 * //Reference : https://developer.android.com/guide/topics/ui/layout/gridview
 */

public class ImageAdapter extends BaseAdapter{
    private Context mContext;

    private Movie [] allMovies;

    public ImageAdapter(Context c, Movie [] movies) {
        mContext = c;
        allMovies = movies;

    }

    public int getCount() {
        return allMovies.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        //let Picasso load image using complete image path
        Picasso.with(mContext).load(Movie.IMAGE_PATH.concat(allMovies[position].getposterPath())).into(imageView);
        //set imageID with position to be used when image is clicked
        imageView.setId(position);

        //set the onClickListener
        //imageView.setOnClickListener(this);
        return imageView;
    }

}
