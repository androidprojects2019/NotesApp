package com.example.notesapp.DataBase;

import android.content.Context;

import com.example.notesapp.DataBase.DAOs.NotesDao;
import com.example.notesapp.DataBase.Model.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase dataBase;

    private static final String DB_NAME = "NotesDataBase";

    public abstract NotesDao notesDao();

    public MyDataBase() {
    }

    public static MyDataBase getInstance(Context context) {
        if (dataBase == null) {
            // Create database
            dataBase = Room.databaseBuilder(context, MyDataBase.class,
                    DB_NAME).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return dataBase;
    }

}
