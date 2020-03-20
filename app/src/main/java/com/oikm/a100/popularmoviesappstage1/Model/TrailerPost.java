package com.oikm.a100.popularmoviesappstage1.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerPost {

    @SerializedName("results")
    private List<Trailers> results ;

    public List<Trailers> getResults() {
        return results;
    }

    public void setResults(List<Trailers> results) {
        this.results = results;
    }

}
