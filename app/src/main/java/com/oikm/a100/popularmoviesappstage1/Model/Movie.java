package com.oikm.a100.popularmoviesappstage1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title, poster, release, overview;
    private double vote;
    private int movie_id;

    public Movie(){

    }
    public Movie(String title, String poster, String release, String overview,double vote,int movie_id){
        this.title = title;
        this.poster = poster;
        this.release = release;
        this.overview = overview;
        this.vote = vote;
        this.movie_id = movie_id;
    }
    public Movie(Parcel in) {
        movie_id = in.readInt();
        vote = in.readDouble();
        release = in.readString();
        overview = in.readString();
        poster = in.readString();
        title = in.readString();
    }
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeDouble(vote);
        dest.writeInt(movie_id);
        dest.writeString(release);
    }
}
