package com.oikm.a100.popularmoviesappstage1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewPost {
    @SerializedName("results")
    private List<Reviews> results;

    public List<Reviews> getResults() {
        return results;
    }

    public void setResults(List<Reviews> results) {
        this.results = results;
    }
}
