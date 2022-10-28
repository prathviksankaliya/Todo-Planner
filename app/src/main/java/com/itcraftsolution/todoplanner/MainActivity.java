package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.SearchView;
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
    private Timer timer;

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

        binding.edSearchNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(list.size() != 0)
                {
                    Toast.makeText(MainActivity.this, ""+editable.toString(), Toast.LENGTH_SHORT).show();
                    searchNotes(editable.toString());
                }
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
                list = notes;
                adapter.updateNotesList(notes);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void searchNotes(String searchKey)
    {
        searchList = new ArrayList<>(list);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKey.trim().isEmpty())
                {

                    list = searchList;
                }else{
                    ArrayList<Notes> tempList = new ArrayList<>();
                    for(Notes notes : searchList)
                    {
                        if(notes.getNotesTitle().toLowerCase().contains(searchKey.toLowerCase()) ||
                                notes.getNotes().toLowerCase().contains(searchKey.toLowerCase()))
                        {
                            tempList.add(notes);
                        }
                    }
                    list = tempList;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateNotesList(list);
                    }
                });
            }
        }, 200);

    }

    public void cancelTimer()
    {
        if(timer != null)
        {
            timer.cancel();
        }
    }
}