package com.oikm.a100.popularmoviesappstage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oikm.a100.popularmoviesappstage1.Database.FavouriteDatabase;
import com.oikm.a100.popularmoviesappstage1.Database.FavouriteTable;
import com.oikm.a100.popularmoviesappstage1.Model.Movie;

public class DetailViewModel extends ViewModel {
    private LiveData<FavouriteTable> table;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public DetailViewModel(FavouriteDatabase database, int id) {
        table = database.favouriteDao().loadMovieById(id);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<FavouriteTable> getTable() {
        return table;
    }
}
