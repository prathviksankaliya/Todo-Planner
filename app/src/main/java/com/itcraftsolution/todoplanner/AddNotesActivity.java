package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.todoplanner.model.Notes;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.ActivityAddNotesBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotesActivity extends AppCompatActivity {

    private ActivityAddNotesBinding binding;
    private NotesViewModel notesViewModel;
    private int id;
    private String selectedNoteColor;
    private boolean pin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        String date = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(new Date());
        binding.txEditDate.setText(date);

        selectedNoteColor = "#f5f5f5";
        updateNotes();
        chooseColor();
        setNoteBackground();

        binding.igBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                    Notes notes = new Notes(binding.edTitle.getText().toString().trim(), binding.edNotes.getText().toString().trim(),date, pin, selectedNoteColor);
                    if(binding.btnAddNoteSave.getText().toString().equals("Save"))
                    {
                        notesViewModel.addNotes(notes);
                        Toast.makeText(AddNotesActivity.this, "Notes Saved!!", Toast.LENGTH_SHORT).show();
                    }else if(binding.btnAddNoteSave.getText().toString().equals("Update"))
                    {
                        Notes updateNotes = new Notes(id,binding.edTitle.getText().toString().trim(), binding.edNotes.getText().toString().trim(),date, pin, selectedNoteColor);
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
            pin = getIntent().getBooleanExtra("pin", false);

            selectedNoteColor = getIntent().getStringExtra("color");
            binding.edNotes.setText(notes);
            binding.edTitle.setText(title);
            binding.btnAddNoteSave.setText("Update");
            getColorIdUsingColorName(selectedNoteColor);

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

    private void chooseColor()
    {
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetColor.bottomsheet);
        binding.bottomSheetColor.bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        binding.bottomSheetColor.viewColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setViewBackground("#f5f5f5", R.drawable.ic_baseline_check_24, 0, 0, 0, 0, 0, 0);
            }
        });
        binding.bottomSheetColor.viewColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setViewBackground("#c5cae9", 0, R.drawable.ic_baseline_check_24, 0, 0, 0, 0, 0);
            }
        });
        binding.bottomSheetColor.viewColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setViewBackground("#ffcdd2", 0, 0, R.drawable.ic_baseline_check_24, 0, 0, 0, 0);
            }
        });
        binding.bottomSheetColor.viewColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setViewBackground("#e1bee7", 0, 0, 0, R.drawable.ic_baseline_check_24, 0, 0, 0);
            }
        });
        binding.bottomSheetColor.viewColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setViewBackground("#bbdefb", 0, 0, 0, 0, R.drawable.ic_baseline_check_24, 0, 0);
            }
        });
        binding.bottomSheetColor.viewColor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewBackground("#c8e6c9", 0, 0, 0, 0, 0, R.drawable.ic_baseline_check_24, 0);
            }
        });
        binding.bottomSheetColor.viewColor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewBackground("#ffecb3", 0, 0, 0, 0, 0, 0, R.drawable.ic_baseline_check_24);
            }
        });

    }
    private void setViewBackground(String color, int igColor1, int igColor2, int igColor3,int igColor4,int igColor5,int igColor6, int igColor7)
    {
        selectedNoteColor = color;
        binding.bottomSheetColor.igColor1.setImageResource(igColor1);
        binding.bottomSheetColor.igColor2.setImageResource(igColor2);
        binding.bottomSheetColor.igColor3.setImageResource(igColor3);
        binding.bottomSheetColor.igColor4.setImageResource(igColor4);
        binding.bottomSheetColor.igColor5.setImageResource(igColor5);
        binding.bottomSheetColor.igColor6.setImageResource(igColor6);
        binding.bottomSheetColor.igColor7.setImageResource(igColor7);
        setNoteBackground();
    }
    private void getColorIdUsingColorName(String colorName)
    {
        if(colorName.equals("#c5cae9"))
        {
            setViewBackground(colorName, 0, R.drawable.ic_baseline_check_24, 0, 0, 0, 0, 0);
        }else if(colorName.equals("#ffcdd2"))
        {
            setViewBackground(colorName, 0, 0, R.drawable.ic_baseline_check_24, 0, 0, 0, 0);
        }else if(colorName.equals("#e1bee7"))
        {
            setViewBackground(colorName, 0, 0, 0, R.drawable.ic_baseline_check_24, 0, 0, 0);

        }else if(colorName.equals("#bbdefb"))
        {
            setViewBackground(colorName, 0, 0, 0, 0, R.drawable.ic_baseline_check_24, 0, 0);

        }else if(colorName.equals("#c8e6c9"))
        {
            setViewBackground(colorName, 0, 0, 0, 0, 0, R.drawable.ic_baseline_check_24, 0);

        }else if(colorName.equals("#ffecb3"))
        {
            setViewBackground(colorName, 0, 0, 0, 0, 0, 0, R.drawable.ic_baseline_check_24);
        }else{
            setViewBackground(colorName, R.drawable.ic_baseline_check_24, 0, 0, 0, 0, 0, 0);
        }
    }

    private void setNoteBackground()
    {
        binding.mainLayout.setBackgroundColor(Color.parseColor(selectedNoteColor));
        binding.edTitle.setBackgroundColor(Color.parseColor(selectedNoteColor));
        binding.edNotes.setBackgroundColor(Color.parseColor(selectedNoteColor));

    }
}