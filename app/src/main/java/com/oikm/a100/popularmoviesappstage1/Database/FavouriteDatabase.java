package com.oikm.a100.popularmoviesappstage1.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = FavouriteTable.class,version = 1, exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase {
    private static final String databaseName = "movieDatabase";
    private static final Object LOCK = new Object();
    private static FavouriteDatabase dInstance;
    public static FavouriteDatabase getInstance(Context context){
        if (dInstance == null){
            synchronized (LOCK){
               dInstance = Room.databaseBuilder(context.getApplicationContext()
                       ,FavouriteDatabase.class,databaseName)
                       .build();
            }
        }
        return dInstance;
    }
    public abstract FavouriteDao favouriteDao();
}
