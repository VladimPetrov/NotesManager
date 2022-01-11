package ru.gb.notesmanager.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.R;

public class MainActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "@@@";
    private static final int NOTE_REQUEST_CODE = 10;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRepository = App.get(this).getNoteRepository();
        Log.d("@@@", "noteRepository.size = " + noteRepository.getNotes().size());
        initRecycler();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter();
        adapter.setNoteList(noteRepository.getNotes());
        adapter.setOnNoteListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteEmployee(NotesEntity noteEntity) {
        noteRepository.deleteNote(noteEntity);
        adapter.setNoteList(noteRepository.getNotes());
    }

    @Override
    public void onClickEmployee(NotesEntity noteEntity) {
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra(NoteActivity.NOTE_EXTRA_KEY,noteEntity);
        //startActivity(intent);
        startActivityForResult(intent, NOTE_REQUEST_CODE);
    }

    @Override
    public void onUpdateEmployee(NotesEntity noteEntity) {
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra(NoteActivity.NOTE_EXTRA_KEY,noteEntity);
        //startActivity(intent);
        startActivityForResult(intent, NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.setNoteList(noteRepository.getNotes());
        }
    }
}