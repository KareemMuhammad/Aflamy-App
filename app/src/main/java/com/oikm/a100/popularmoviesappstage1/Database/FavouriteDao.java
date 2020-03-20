package com.oikm.a100.popularmoviesappstage1.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insert(FavouriteTable favouriteTable);

    @Delete
     void delete(FavouriteTable favouriteTable);

    @Query("SELECT * FROM favouriteMovies WHERE movie_id = :id" )
   LiveData<FavouriteTable> loadMovieById(int id);

    @Query("SELECT * FROM favouriteMovies ORDER BY movie_id")
    LiveData<List<FavouriteTable>> loadAllMovies();

}
