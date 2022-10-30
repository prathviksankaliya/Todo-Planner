package com.itcraftsolution.todoplanner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.itcraftsolution.todoplanner.Adapters.RvAllNotesAdapter;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.ActivityFavNotesBinding;
import com.itcraftsolution.todoplanner.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class FavNotesActivity extends AppCompatActivity {

    ActivityFavNotesBinding binding;
    RvAllNotesAdapter adapter;
    List<Notes> favNotes;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        favNotes = new ArrayList<>();
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.igBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getAllFavNotes();

        adapter = new RvAllNotesAdapter(this, favNotes);
        binding.rvFavNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.rvFavNotes.setAdapter(adapter);
    }

    private void getAllFavNotes() {
        notesViewModel.getAllFavNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if (notes.isEmpty()) {
                    binding.notFoundLayout.setVisibility(View.VISIBLE);
                    binding.rvFavNotes.setVisibility(View.GONE);
                } else {
                    binding.notFoundLayout.setVisibility(View.GONE);
                    binding.rvFavNotes.setVisibility(View.VISIBLE);
                    favNotes = notes;
                    adapter.updateNotesList(notes);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}