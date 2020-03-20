package com.oikm.a100.popularmoviesappstage1;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.oikm.a100.popularmoviesappstage1.Database.FavouriteDatabase;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private FavouriteDatabase fdb;
    private int id;

    public DetailViewModelFactory(FavouriteDatabase fdb, int id) {
        this.fdb = fdb;
        this.id = id;
    }

    @Override
    public <T extends ViewModel> T create( Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(fdb, id);
    }
}
