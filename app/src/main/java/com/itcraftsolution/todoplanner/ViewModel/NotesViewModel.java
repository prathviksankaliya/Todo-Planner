package com.itcraftsolution.todoplanner.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itcraftsolution.todoplanner.Database.Notes;
import com.itcraftsolution.todoplanner.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository notesRepository;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
    }

    public LiveData<List<Notes>> getAllNotes()
    {
        return notesRepository.getAllNotes();
    }

    public void addNotes(Notes notes)
    {
         notesRepository.addNotes(notes);
    }

    public void deleteNotes(int id)
    {
        notesRepository.deleteNotes(id);
    }

    public void updateNotes(Notes notes)
    {
        notesRepository.updateNotes(notes);
    }
}
