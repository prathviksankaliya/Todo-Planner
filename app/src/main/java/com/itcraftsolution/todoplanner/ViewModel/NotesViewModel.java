package com.itcraftsolution.todoplanner.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itcraftsolution.todoplanner.model.Notes;
import com.itcraftsolution.todoplanner.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository notesRepository;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
    }

    public LiveData<List<Notes>> getAllNotes() {
        return notesRepository.getAllNotes();
    }

    public LiveData<List<Notes>> getAllFavNotes() {
        return notesRepository.getAllFavNotes();
    }

    public boolean getFavNotes(int id) {
        return notesRepository.getFavNotes(id);
    }

    public void addNotes(Notes notes) {
        notesRepository.addNotes(notes);
    }

    public void deleteNotes(int id) {
        notesRepository.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesRepository.updateNotes(notes);
    }

    public void favNotes(int id, boolean pin) {
        notesRepository.favNotes(id, pin);
    }
}
