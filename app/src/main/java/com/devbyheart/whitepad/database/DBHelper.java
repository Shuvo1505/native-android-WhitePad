package com.devbyheart.whitepad.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devbyheart.whitepad.models.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class DBHelper extends RoomDatabase {
    public static DBHelper storebase;
    public static String DATABASE_NAME = "usernotes";

    public synchronized static DBHelper getInstance(Context context) {
        if (storebase == null) {
            storebase = Room.databaseBuilder(context.getApplicationContext(),
                            DBHelper.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return storebase;
    }

    public abstract MainDAO mainDAO();
}