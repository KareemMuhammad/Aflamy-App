package com.oikm.a100.popularmoviesappstage1.Utilities;

import android.content.Context;

import com.oikm.a100.popularmoviesappstage1.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParsing {

    private static final String Key_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String Key_POSTER_SIZE = "w185";
    private static final String Key_POSTER_PATH = "poster_path";
    private static final String Key_TITLE = "title";
    private static final String Key_VOTE = "vote_average";
    private static final String Key_OVERVIEW = "overview";
    private static final String Key_RELEASE_DATE = "release_date";
    private static final String Key_Results = "results";
    private static final String Key_Id = "id";

    public static Movie[] parseMovieJson(String json, Context context){
        if (json == null || json.isEmpty()){
            return null;
        }
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(Key_Results);
            Movie[] movieArray = new Movie[results.length()];
            for (int i = 0 ;i < results.length();i++) {
                Movie movie = new Movie();
                String poster = results.getJSONObject(i).optString(Key_POSTER_PATH);
                String title = results.getJSONObject(i).optString(Key_TITLE);
                double voteAverage = results.getJSONObject(i).optDouble(Key_VOTE);
                String overview = results.getJSONObject(i).optString(Key_OVERVIEW);
                String release = results.getJSONObject(i).optString(Key_RELEASE_DATE);
                int id = results.getJSONObject(i).optInt(Key_Id);

                movie.setPoster(Key_BASE_URL + Key_POSTER_SIZE + poster);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setRelease(release);
                movie.setVote(voteAverage);
                movie.setMovie_id(id);
                movieArray[i] = movie;
            }
            return movieArray;

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
