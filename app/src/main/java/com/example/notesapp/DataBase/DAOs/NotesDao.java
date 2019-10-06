package com.example.notesapp.DataBase.DAOs;


import com.example.notesapp.DataBase.Model.Note;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotesDao {

    @Insert
    void addNote(Note note);

    @Query("Update note Set title =:title,content = :content,dateTime = :dateTime")
    void updateNote(String title, String content, String dateTime);

    @Delete
    void deleteNote(Note note);

    @Query("delete from Note where id = :id")
    void deleteNote(int id);

    @Query("select * from Note where title = :title")
    List<Note> searchNoteByTitle(String title);

    @Query("select * from note")
    List<Note> getAllNotes();
}
