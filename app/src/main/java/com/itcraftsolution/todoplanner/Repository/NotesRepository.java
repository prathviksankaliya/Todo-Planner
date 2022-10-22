package com.itcraftsolution.todoplanner.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.itcraftsolution.todoplanner.Database.DatabaseHelper;
import com.itcraftsolution.todoplanner.Database.Notes;

import java.util.List;

public class NotesRepository {

    private DatabaseHelper databaseHelper;

    public NotesRepository(Application application) {
        databaseHelper = DatabaseHelper.databaseHelper(application);
    }

    public LiveData<List<Notes>> getAllNotes()
    {
        return databaseHelper.databaseInterface().getAllLiveNotes();
    }

    public void addNotes(Notes notes)
    {
        databaseHelper.databaseInterface().addNotes(notes);
    }

    public void deleteNotes(int id)
    {
        databaseHelper.databaseInterface().deleteNotes(id);
    }

    public void updateNotes(Notes notes)
    {
        databaseHelper.databaseInterface().updateNotes(notes);
    }
}
