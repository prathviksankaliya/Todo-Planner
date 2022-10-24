package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.todoplanner.Database.Notes;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.ActivityAddNotesBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotesActivity extends AppCompatActivity {

    private ActivityAddNotesBinding binding;
    private NotesViewModel notesViewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        updateNotes();

        binding.btnAddNoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edTitle.getText().toString().isEmpty())
                {
                    Snackbar.make(binding.mainLayout, "Please Set Notes Title!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.edTitle.requestFocus();
                }else if(binding.edNotes.getText().toString().isEmpty()) {
                    Snackbar.make(binding.mainLayout, "Please Set Notes Text!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.edNotes.requestFocus();
                }else {
                    String date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
                    Notes notes = new Notes(binding.edTitle.getText().toString(), binding.edNotes.getText().toString(),date, false);
                    if(binding.btnAddNoteSave.getText().toString().equals("Save"))
                    {
                        notesViewModel.addNotes(notes);
                        Toast.makeText(AddNotesActivity.this, "Notes Saved!!", Toast.LENGTH_SHORT).show();
                    }else if(binding.btnAddNoteSave.getText().toString().equals("Update"))
                    {
                        Notes updateNotes = new Notes(id,binding.edTitle.getText().toString(), binding.edNotes.getText().toString(),date, false);
                        notesViewModel.updateNotes(updateNotes);
                        Toast.makeText(AddNotesActivity.this, "Notes Updated!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    private void updateNotes()
    {
        if(getIntent().getBooleanExtra("update", false))
        {
            String notes,title;
            notes = getIntent().getStringExtra("notes");
            id = getIntent().getIntExtra("id",0);
            title = getIntent().getStringExtra("title");
            binding.edNotes.setText(notes);
            binding.edTitle.setText(title);
            binding.btnAddNoteSave.setText("Update");

        }else{
            binding.edNotes.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addnote_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}