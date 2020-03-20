package com.oikm.a100.popularmoviesappstage1.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favouriteMovies")
public class FavouriteTable {
    private String title, poster, release, overview;
    private double vote;
    @PrimaryKey(autoGenerate = true)
    private int movie_id;

    public FavouriteTable(String title, String poster, String release, String overview,double vote,int movie_id){
        this.title = title;
        this.poster = poster;
        this.release = release;
        this.overview = overview;
        this.vote = vote;
        this.movie_id = movie_id;
    }
    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
