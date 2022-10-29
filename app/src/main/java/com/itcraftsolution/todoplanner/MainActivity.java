package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itcraftsolution.todoplanner.Adapters.RvAllNotesAdapter;
import com.itcraftsolution.todoplanner.model.Notes;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RvAllNotesAdapter adapter;
    private NotesViewModel notesViewModel;
    private List<Notes> list, searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        fetchAllNotes();
        adapter = new RvAllNotesAdapter(this, list);
        binding.rvAllNotes.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        binding.rvAllNotes.setAdapter(adapter);

        binding.edSearchNotes.clearFocus();
        binding.edSearchNotes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchNotes(newText);
                return true;
            }
        });

        binding.fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
            }
        });

        binding.btnHomeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavNotesActivity.class));
            }
        });
    }

    private void fetchAllNotes()
    {
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if(notes.isEmpty())
                {
                    binding.rvAllNotes.setVisibility(View.GONE);
                    binding.notFoundLayout.setVisibility(View.VISIBLE);
                }else{
                    binding.rvAllNotes.setVisibility(View.VISIBLE);
                    binding.notFoundLayout.setVisibility(View.GONE);
                    list = notes;
                    adapter.updateNotesList(notes);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void searchNotes(String searchKey)
    {
        searchList = new ArrayList<>();
        searchKey = searchKey.toLowerCase().trim();
        for(Notes notes : list)
        {
            if(notes.getNotesTitle().toLowerCase().trim().contains(searchKey) || notes.getNotes().toLowerCase().trim().contains(searchKey))
            {
                searchList.add(notes);
            }
        }
        if(searchList.isEmpty())
        {
            binding.notFoundLayout.setVisibility(View.VISIBLE);
            binding.rvAllNotes.setVisibility(View.GONE);
        }else{
            adapter.updateNotesList(searchList);
            binding.rvAllNotes.setVisibility(View.VISIBLE);
            binding.notFoundLayout.setVisibility(View.GONE);
        }
    }
}