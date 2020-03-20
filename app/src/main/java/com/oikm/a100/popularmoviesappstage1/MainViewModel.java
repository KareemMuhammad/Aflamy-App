package com.oikm.a100.popularmoviesappstage1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.oikm.a100.popularmoviesappstage1.Database.FavouriteDatabase;
import com.oikm.a100.popularmoviesappstage1.Database.FavouriteTable;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
   private LiveData<List<FavouriteTable>> favTableTask;
    public MainViewModel( Application application ) {
        super(application);
        FavouriteDatabase fdb = FavouriteDatabase.getInstance(this.getApplication());
        favTableTask = fdb.favouriteDao().loadAllMovies();
    }

    public LiveData<List<FavouriteTable>> getFavTable() {
        return favTableTask;
    }
}
