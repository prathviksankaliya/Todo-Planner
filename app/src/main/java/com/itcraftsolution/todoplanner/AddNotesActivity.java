package com.itcraftsolution.todoplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.itcraftsolution.todoplanner.databinding.ActivityAddNotesBinding;

public class AddNotesActivity extends AppCompatActivity {

    private ActivityAddNotesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}