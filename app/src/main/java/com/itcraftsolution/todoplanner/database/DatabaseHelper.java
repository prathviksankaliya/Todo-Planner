package com.itcraftsolution.todoplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.itcraftsolution.todoplanner.model.Notes;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    private static DatabaseHelper instance;
    private static String dbName = "NotesDatabase";
    public abstract DatabaseInterface databaseInterface();


    public static synchronized DatabaseHelper databaseHelper(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, dbName)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
