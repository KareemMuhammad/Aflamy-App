package com.oikm.a100.popularmoviesappstage1.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiPlaceHolderReview {
    @GET("movie/{id}/reviews")
    Call<ReviewPost> getResults(
            @Path("id") int movieId,
            @Query("api_key")String apiKey
    );
}
