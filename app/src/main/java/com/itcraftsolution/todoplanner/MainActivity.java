package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.itcraftsolution.todoplanner.Adapters.RvAllNotesAdapter;
import com.itcraftsolution.todoplanner.Database.Notes;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RvAllNotesAdapter adapter;
    private NotesViewModel notesViewModel;
    private List<Notes> list, filterNotes;
    private EditText searchEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.materialToolbar);
        list = new ArrayList<>();
        filterNotes = new ArrayList<>();
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        fetchAllNotes();

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
            }
        });
    }

    private void fetchAllNotes()
    {
        adapter = new RvAllNotesAdapter(this, list);
        binding.rvAllNotes.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        binding.rvAllNotes.setAdapter(adapter);

        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                list = notes;
                filterNotes = notes;
                adapter.updateNotesList(notes);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search any Note words...");
        searchEdittext = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchEdittext.setHintTextColor(Color.WHITE);
//        searchEdittext.setTextColor(Color.GRAY);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilterNotes(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getFilterNotes(String s){
        String query = s.toLowerCase(Locale.ROOT);
        List<Notes> list = new ArrayList<>();
        for(Notes notes : filterNotes)
        {
            if(notes.getNotesTitle().contains(query) || notes.getNotes().contains(query) || notes.getNotesDate().contains(query))
            {
                list.add(notes);
            }
        }
        adapter.searchNotes(list);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}