package com.itcraftsolution.todoplanner.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Query("select * from tbl_Notes")
    LiveData<List<Notes>> getAllLiveNotes();

    @Insert
    void addNotes(Notes notes);

    @Query("delete from tbl_Notes where id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);
}
