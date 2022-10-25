package com.itcraftsolution.todoplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.itcraftsolution.todoplanner.model.Notes;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Query("select * from tbl_Notes Order by id desc")
    LiveData<List<Notes>> getAllLiveNotes();

    @Insert
    void addNotes(Notes notes);

    @Query("delete from tbl_Notes where id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);
}
