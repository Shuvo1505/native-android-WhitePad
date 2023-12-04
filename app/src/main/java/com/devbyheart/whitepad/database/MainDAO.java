package com.devbyheart.whitepad.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.devbyheart.whitepad.models.Notes;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    List<Notes> getall();

    @Query("UPDATE notes SET title = :titile , notes = :notes WHERE ID = :id")
    void update(int id, String notes, String titile);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);
}