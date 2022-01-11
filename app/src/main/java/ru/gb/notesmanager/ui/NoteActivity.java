package ru.gb.notesmanager.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NotesEntity;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView noteTextView;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);
        initView();
    }

    private void initView() {
        titleTextView = findViewById(R.id.title_note_text_view);
        dateTextView = findViewById(R.id.date_note_text_view);
        noteTextView = findViewById(R.id.body_note_text_view);
        deleteButton = findViewById(R.id.delete_button);
        NotesEntity notesEntity = getIntent().getParcelableExtra(NOTE_EXTRA_KEY);
        titleTextView.setText(notesEntity.getTitle());
        dateTextView.setText(notesEntity.getDate());
        noteTextView.setText(notesEntity.getTextNote());

        deleteButton.setOnClickListener(v -> {
            App.get(this).getNoteRepository().deleteNote(notesEntity);
            setResult(RESULT_OK);
            finish();
        });

    }
}
