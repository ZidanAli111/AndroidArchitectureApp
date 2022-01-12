package com.example.androidarchitectureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidarchitectureapp.data.Note;
import com.example.androidarchitectureapp.viewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update RecyclerView
                Toast.makeText(MainActivity.this,"onChanged",Toast.LENGTH_SHORT).show();
            }
        });
    }
}