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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edNotes.requestFocus();

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.igBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
                    String date = new SimpleDateFormat("dd MM yyyy", Locale.getDefault()).format(new Date());
                    Notes notes = new Notes(binding.edTitle.getText().toString(), binding.edNotes.getText().toString(),date, false);
                    notesViewModel.addNotes(notes);
                    Toast.makeText(AddNotesActivity.this, "Saved!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }


}