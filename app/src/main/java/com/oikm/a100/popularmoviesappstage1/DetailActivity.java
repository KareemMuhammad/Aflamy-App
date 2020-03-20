package com.oikm.a100.popularmoviesappstage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oikm.a100.popularmoviesappstage1.Database.FavouriteDatabase;
import com.oikm.a100.popularmoviesappstage1.Database.FavouriteTable;
import com.oikm.a100.popularmoviesappstage1.Model.JsonApiPlaceHolderReview;
import com.oikm.a100.popularmoviesappstage1.Model.JsonApiPlaceHolderTrailer;
import com.oikm.a100.popularmoviesappstage1.Model.Movie;
import com.oikm.a100.popularmoviesappstage1.Model.ReviewPost;
import com.oikm.a100.popularmoviesappstage1.Model.Reviews;
import com.oikm.a100.popularmoviesappstage1.Model.TrailerPost;
import com.oikm.a100.popularmoviesappstage1.Model.Trailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.OnItemClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();
    public ImageView imagePoster;
    public TextView title;
    public TextView overview;
    public TextView voteAverage;
    public TextView releaseDate;
    public TextView review;
    public RecyclerView trailerRecyclerView;
    public ImageView starIcon;
    public TrailerPost trailerPost;
    public ReviewPost reviewPost;
    public TrailerAdapter trailerAdapter;
    private static final String Api_Key = "api";
    private boolean star = false;
    private Movie movie;
    private FavouriteDatabase fdb;
    private List<Reviews> reviewList;
    private List<Trailers> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        populateUI();
        fdb = FavouriteDatabase.getInstance(this.getApplicationContext());
        final Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String poster = intent.getStringExtra("Poster");
            String titleText = intent.getStringExtra("Title");
            String overviewText = intent.getStringExtra("Overview");
            String releaseText = intent.getStringExtra("Release Date");
            double voteText = intent.getDoubleExtra("Vote Average", 0.0);
            int id = intent.getIntExtra("Movie_Id", 0);
            movie = new Movie(titleText,poster,releaseText,overviewText,voteText,id);
            Picasso.get().load(poster).into(imagePoster);
            title.setText(titleText);
            overview.setText(overviewText);
            releaseDate.setText(releaseText);
            voteAverage.setText(String.valueOf(voteText));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonApiPlaceHolderTrailer jsonApiPlaceHolderTrailer = retrofit.create(JsonApiPlaceHolderTrailer.class);
            JsonApiPlaceHolderReview jsonApiPlaceHolderReview = retrofit.create(JsonApiPlaceHolderReview.class);
            Call<TrailerPost> trailerCall = jsonApiPlaceHolderTrailer.getResults(id, Api_Key);
            Call<ReviewPost> reviewCall = jsonApiPlaceHolderReview.getResults(id, Api_Key);
            trailerCall.enqueue(new Callback<TrailerPost>() {
                @Override
                public void onResponse(Call<TrailerPost> call, Response<TrailerPost> response) {
                    if (response.isSuccessful()) {
                        trailerPost = response.body();
                        if (trailerPost != null) {
                            trailerList = trailerPost.getResults();
                            Log.d(TAG, "trailerCallEnqueue: get results "+trailerList.get(0).getName());
                            trailerAdapter.setData(trailerList);
                            trailerAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Log.d(TAG, "trailerCallEnqueue: call failed "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<TrailerPost> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            reviewCall.enqueue(new Callback<ReviewPost>() {
                @Override
                public void onResponse(Call<ReviewPost> call, Response<ReviewPost> response) {
                    if (response.isSuccessful()) {
                        reviewPost = response.body();
                        if (reviewPost != null) {
                            reviewList = reviewPost.getResults();
                            for (Reviews results : reviewList) {
                                String lines = "";
                                lines += " Author : " + results.getAuthor() + '\n';
                                lines += "Content : " + results.getContent() + '\n';
                                review.setText(lines);
                            }

                        }
                    }else
                        review.setText(String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Call<ReviewPost> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


            DetailViewModelFactory viewModelFactory = new DetailViewModelFactory(fdb,id);
            DetailViewModel viewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailViewModel.class);
            viewModel.getTable().observe(this, new Observer<FavouriteTable>() {
                @Override
                public void onChanged( FavouriteTable movieItem) {
                    if (movieItem != null) {
                        if (movie.getMovie_id() == movieItem.getMovie_id()) {
                            setFavorite(true);
                        } else
                            setFavorite(false);
                    }
                }
            });
        }
        starIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavStar();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void populateUI(){
        imagePoster = findViewById(R.id.poster_image);
        voteAverage = findViewById(R.id.voteText);
        overview = findViewById(R.id.overviewText);
        title = findViewById(R.id.titleText_tv);
        releaseDate = findViewById(R.id.releaseText);
        trailerRecyclerView = findViewById(R.id.trailersRecycleView);
        review = findViewById(R.id.reviewText);
        starIcon = findViewById(R.id.favouriteStar);
        trailerPost = new TrailerPost();
        reviewPost = new ReviewPost();
        reviewList = new ArrayList<com.oikm.a100.popularmoviesappstage1.Model.Reviews>();
        trailerList = new ArrayList<Trailers>();
        trailerAdapter = new TrailerAdapter(trailerPost.getResults(), this);
        trailerRecyclerView.setAdapter(trailerAdapter);
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClickListener(int position) {

        String link = "https://www.youtube.com/watch?v="+ trailerPost.getResults().get(position).getKey();
        Uri youTubePage = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, youTubePage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void setFavorite(Boolean fav){
        if (fav) {
            star = true;
            starIcon.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            star = false;
            starIcon.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }

    public void onClickFavStar() {
         final FavouriteTable fTable = new FavouriteTable(movie.getTitle()
                ,movie.getPoster()
                ,movie.getRelease()
                ,movie.getOverview()
                ,movie.getVote()
                ,movie.getMovie_id());
         AppExecutors.getInstance().diskIO().execute(new Runnable() {
             @Override
             public void run() {
                 if (star) {
                     fdb.favouriteDao().delete(fTable);
                     Log.d(TAG, "data deleted");
                 }else {
                     fdb.favouriteDao().insert(fTable);
                     Log.d(TAG, "data inserted");
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         setFavorite(!star);
                     }
                 });
                     finish();
             }
         });

    }
}
