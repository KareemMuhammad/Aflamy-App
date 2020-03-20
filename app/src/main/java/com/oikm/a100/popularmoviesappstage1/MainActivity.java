package com.oikm.a100.popularmoviesappstage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oikm.a100.popularmoviesappstage1.Database.FavouriteDatabase;
import com.oikm.a100.popularmoviesappstage1.Database.FavouriteTable;
import com.oikm.a100.popularmoviesappstage1.Model.Movie;
import com.oikm.a100.popularmoviesappstage1.Utilities.JsonParsing;
import com.oikm.a100.popularmoviesappstage1.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String Key_Movies = "movies";
   public RecyclerView movieRecyclerView;
   public MovieAdapter movieAdapter;
   public Movie[] movieData;
   public TextView errorText;
   public ProgressBar loadingBar;
    String query = "popular";
    private FavouriteDatabase fdb;
    private List<FavouriteTable> favTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            movieData = (Movie[]) savedInstanceState.getParcelableArray(Key_Movies);
        }
        movieRecyclerView = findViewById(R.id.recyclerView);
        errorText = findViewById(R.id.textError);
        loadingBar = findViewById(R.id.progress_bar);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        movieAdapter = new MovieAdapter(movieData,MainActivity.this);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(movieAdapter);
        favTableList = new ArrayList<FavouriteTable>();
        loadMovieData();
        fdb = FavouriteDatabase.getInstance(this.getApplicationContext());
        setupViewModel();
    }
    public void setupViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavTable().observe(this, new Observer<List<FavouriteTable>>() {
            @Override
            public void onChanged(List<FavouriteTable> favouriteTables) {
                if(!favouriteTables.isEmpty()) {
                    favTableList = favouriteTables;
                }
            }
        });
    }

   @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(Key_Movies,movieData);
    }

    public void loadMovieData(){
        showMovieData();
        new FetchMovieTask().execute(query);
    }
    public void showMovieData(){
        errorText.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }
    public void showError(){
        errorText.setVisibility(View.VISIBLE);
        movieRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int moviePosition) {
        Context context = this;
        Class Detail = DetailActivity.class;
        Intent detailIntent = new Intent(context,Detail);
       if (detailIntent.resolveActivity(getPackageManager()) != null){
            detailIntent.putExtra(Intent.EXTRA_TEXT,moviePosition);
                detailIntent.putExtra("Poster", movieData[moviePosition].getPoster());
                detailIntent.putExtra("Title", movieData[moviePosition].getTitle());
                detailIntent.putExtra("Overview", movieData[moviePosition].getOverview());
                detailIntent.putExtra("Vote Average", movieData[moviePosition].getVote());
                detailIntent.putExtra("Release Date", movieData[moviePosition].getRelease());
                detailIntent.putExtra("Movie_Id", movieData[moviePosition].getMovie_id());
                startActivity(detailIntent);

       }
    }

    public class FetchMovieTask extends AsyncTask<String,Void,Movie[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0)
            {
                return null;
            }
            String position = params[0];
            URL movieUrl = NetworkUtilities.buildUrl(position);
            try {
                String movieRequest = NetworkUtilities.getResponseFromHttpUrl(movieUrl);
                 movieData = JsonParsing.parseMovieJson(movieRequest,MainActivity.this);
                return movieData;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Movie[] movie) {
            loadingBar.setVisibility(View.INVISIBLE);
            if (movie == null){
                showError();
            }
            else {
                showMovieData();
                movieAdapter = new MovieAdapter(movie,MainActivity.this);
                movieRecyclerView.setAdapter(movieAdapter);

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.action_popular) {
            query = "popular";
            loadMovieData();
            return true;
        }
        if (menuItemSelected == R.id.action_top_rated) {
            query = "top_rated";
            loadMovieData();
            return true;
        }
        if (menuItemSelected == R.id.action_sort_favorite){
            loadFavouriteMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadFavouriteMovies(){
        if (favTableList != null) {
            Log.d(TAG,"data arrived");
            for (int i = 0; i < favTableList.size(); i++) {
                Movie mov = new Movie();
                mov.setTitle(favTableList.get(i).getTitle());
                mov.setPoster(favTableList.get(i).getPoster());
                mov.setRelease(favTableList.get(i).getRelease());
                mov.setOverview(favTableList.get(i).getOverview());
                mov.setVote(favTableList.get(i).getVote());
                mov.setMovie_id(favTableList.get(i).getMovie_id());
                movieData[i] = mov;
            }
            Log.d(TAG,"data sent to adapter");
            movieAdapter.setData(movieData);
            movieAdapter.notifyDataSetChanged();
        }
    }
}
